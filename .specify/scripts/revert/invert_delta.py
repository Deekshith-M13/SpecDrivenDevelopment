def invert_delta(delta: dict) -> dict:
    reversed_sections = []

    for section in delta["sections"]:
        op = section["operation"]

        if op == "ADDED":
            # Was added forward → remove it on revert
            reversed_sections.append({
                **section,
                "operation": "REMOVE",
                "revert_action": "Delete this section — it was added in the original change."
            })

        elif op == "MODIFIED":
            # Was modified forward → restore previous content
            if not section.get("previous_content"):
                raise ValueError(
                    f"Section '{section['section_id']}' is MODIFIED "
                    f"but has no previous_content to restore."
                )
            reversed_sections.append({
                **section,
                "operation": "RESTORE",
                "revert_action": "Restore to previous_content below.",
                "restore_content": section["previous_content"]
            })

        elif op == "REMOVED":
            # Was removed forward → re-insert it on revert
            reversed_sections.append({
                **section,
                "operation": "REINSERT",
                "revert_action": "Re-insert this section — it was removed in the original change."
            })

        else:
            raise ValueError(f"Unknown operation type: {op}")

    return {
        "source_archive_id": delta["archive_id"],
        "sections": reversed_sections
    }