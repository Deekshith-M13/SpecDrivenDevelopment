import os
from datetime import datetime

def write_reverse_delta(
    inverted: dict,
    openspec_dir: str = "openspec",
    staging_name: str = None
) -> str:

    timestamp = datetime.now().strftime("%Y-%m-%d")
    source_id = inverted["source_archive_id"]
    folder_name = staging_name or f"{timestamp}-revert-{source_id}"
    staging_path = os.path.join(openspec_dir, "changes", folder_name)
    os.makedirs(staging_path, exist_ok=True)

    output_path = os.path.join(staging_path, "reverse-delta-spec.md")

    lines = []
    lines.append("---")
    lines.append("type: reverse-delta")
    lines.append(f"source-archive: {source_id}")
    lines.append(f"generated-at: {datetime.now().isoformat()}")
    lines.append("status: PENDING_REVIEW")
    lines.append("---")
    lines.append("")
    lines.append("# Reverse Delta — Review Before Applying")
    lines.append("")
    lines.append(
        f"> This revert undoes changes from archive: `{source_id}`  "
    )
    lines.append("> Review each section carefully before approving.")
    lines.append("")

    for section in inverted["sections"]:
        lines.append(f"## {section['operation']} {section['section_id']}")
        lines.append("")
        lines.append(f"> **Action:** {section['revert_action']}")
        lines.append("")

        if section["operation"] == "RESTORE":
            lines.append("### Restore to")
            lines.append("```")
            lines.append(section.get("restore_content", ""))
            lines.append("```")

        elif section["operation"] == "REINSERT":
            lines.append("### Content to re-insert")
            lines.append("```")
            lines.append(section.get("body", ""))
            lines.append("```")

        elif section["operation"] == "REMOVE":
            lines.append("### Section to remove")
            lines.append("```")
            lines.append(section.get("body", ""))
            lines.append("```")

        lines.append("")

    with open(output_path, "w") as f:
        f.write("\n".join(lines))

    return output_path