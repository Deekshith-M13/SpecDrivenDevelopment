#!/usr/bin/env python3
import sys
import os
import subprocess


def main():
    if len(sys.argv) < 2:
        print("Usage: speckit-revert <archive-id> [--dry-run]")
        print("\nAvailable archives:")
        list_archives()
        sys.exit(1)

    archive_id = sys.argv[1]
    dry_run = "--dry-run" in sys.argv

    archive_path = os.path.join("specs", "archived", archive_id)
    spec_file = os.path.join(archive_path, "spec.md")

    # Validate archive exists
    if not os.path.exists(archive_path):
        print(f"\n✗ Archive not found: {archive_path}")
        print("\nAvailable archives:")
        list_archives()
        sys.exit(1)

    if not os.path.exists(spec_file):
        print(f"\n✗ No spec.md found in: {archive_path}")
        sys.exit(1)

    print(f"\n→ Found archive: {archive_id}")
    print(f"  Spec: {spec_file}")

    if dry_run:
        print("\n[DRY RUN] Would propose a revert for this archive:")
        print(f"  openspec propose revert-{archive_id}")
        print("\nSpec contents:")
        print("-" * 40)
        with open(spec_file, "r") as f:
            print(f.read())
        print("-" * 40)
        print("\nNo changes made.")
        sys.exit(0)

    # Step 1: Ask OpenSpec to propose the revert
    print(f"\n→ Asking OpenSpec to propose revert of: {archive_id}")
    print("  This will create a new change proposal to undo the archived change.")
    print("\nContinue? (y/N): ", end="")
    choice = input().strip().lower()

    if choice != "y":
        print("✗ Revert cancelled.")
        sys.exit(0)

    # Step 2: Hand off to OpenSpec propose
    revert_name = f"revert-{archive_id}"
    print(f"\n→ Running: openspec propose {revert_name}")
    print("  Tell your AI agent to read the archived spec and reverse the change.")
    print(f"  Archive spec is at: {spec_file}\n")

    result = subprocess.call([
        "openspec", "propose", revert_name,
        "--context", f"Revert the change described in {spec_file}. "
                     f"Read the archived spec and reverse exactly what was done. "
                     f"Archive ID: {archive_id}"
    ])

    if result != 0:
        print("\n✗ OpenSpec propose failed.")
        print("  You can manually run:")
        print(f"  /opsx:propose revert-{archive_id}")
        print(f"  Then reference the spec at: {spec_file}")
        sys.exit(1)

    # Step 3: Review gate before apply
    print("\n→ Review the generated reverse proposal before applying.")
    print(f"  Check: openspec/changes/{revert_name}/")
    print("\nReady to apply? (y/N): ", end="")
    choice = input().strip().lower()

    if choice != "y":
        print(f"\n✗ Apply cancelled. Proposal preserved at: openspec/changes/{revert_name}/")
        print("  Run /opsx:apply manually when ready.")
        sys.exit(0)

    # Step 4: Apply via OpenSpec
    print(f"\n→ Running: openspec apply")
    result = subprocess.call(["openspec", "apply"])

    if result != 0:
        print("\n✗ OpenSpec apply failed.")
        print("  Run /opsx:apply manually in your agent.")
        sys.exit(1)

    # Step 5: Archive the revert itself
    print(f"\n→ Archiving the revert...")
    result = subprocess.call(["openspec", "archive"])

    if result != 0:
        print("\n✗ Archive step failed. Run /opsx:archive manually.")
        sys.exit(1)

    # Step 6: Git commit
    print(f"\n→ Committing revert to Git...")
    subprocess.call(["git", "add", "."])
    subprocess.call([
        "git", "commit", "-m",
        f"revert({archive_id}): change reversed via speckit-revert"
    ])

    print(f"\n✓ Revert complete.")
    print(f"  Original archive : specs/archived/{archive_id}")
    print(f"  Revert archive   : specs/archived/{revert_name}")


def list_archives():
    archives_path = os.path.join("specs", "archived")
    if not os.path.exists(archives_path):
        print("  No archives found — specs/archived/ does not exist.")
        return
    entries = [
        d for d in os.listdir(archives_path)
        if os.path.isdir(os.path.join(archives_path, d))
    ]
    if not entries:
        print("  No archives found.")
    for entry in sorted(entries):
        print(f"  • {entry}")


if __name__ == "__main__":
    main()