import os
import re

def read_archive(archive_id: str) -> dict:
    archive_path = os.path.join("specs", "archived", archive_id)

    if not os.path.exists(archive_path):
        raise FileNotFoundError(f"Archive not found: {archive_path}")

    spec_file = os.path.join(archive_path, "spec.md")

    if not os.path.exists(spec_file):
        raise FileNotFoundError(f"No spec.md found in archive: {archive_path}")

    with open(spec_file, "r") as f:
        content = f.read()

    sections = parse_delta_sections(content, spec_file)

    return {
        "archive_id": archive_id,
        "archive_path": archive_path,
        "sections": sections
    }


def parse_delta_sections(content: str, source_file: str) -> list:
    sections = []
    pattern = re.compile(
        r"##\s+(ADDED|MODIFIED|REMOVED)\s+(.+?)\n(.*?)(?=\n##\s+(?:ADDED|MODIFIED|REMOVED)|\Z)",
        re.DOTALL
    )
    for match in pattern.finditer(content):
        operation = match.group(1).strip()
        section_id = match.group(2).strip()
        body = match.group(3).strip()

        prev_content = None
        prev_match = re.search(
            r"### previous_content\s*\n(.*?)(?=\n###|\Z)", body, re.DOTALL
        )
        if prev_match:
            prev_content = prev_match.group(1).strip()

        sections.append({
            "operation": operation,
            "section_id": section_id,
            "body": body,
            "previous_content": prev_content,
            "source_file": source_file
        })
    return sections