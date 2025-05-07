#!/bin/bash

STATUS=$1

# Slack Notification
if [[ -n "$SLACK_WEBHOOK_URL" ]]; then
  echo "=== Sending Slack Notification ==="
  MESSAGE="*Luma Selenium Test Results*\n$( [ "$STATUS" -eq 0 ] && echo "✅ All tests passed!" || echo "❌ Some tests failed!" )"
  curl -X POST -H "Content-type: application/json" --data "{
    \"text\": \"$MESSAGE\",
    \"attachments\": [
      {
        \"text\": \"OS: Docker | Status: $( [ $STATUS -eq 0 ] && echo PASSED || echo FAILED )\",
        \"color\": \"$( [ $STATUS -eq 0 ] && echo good || echo danger )\"
      }
    ]
  }" "$SLACK_WEBHOOK_URL"
fi


if [[ -n "$EMAIL_SENDER" && -n "$EMAIL_RECIPIENT" ]]; then
  echo "=== Sending Email ==="
  SUBJECT="Luma Test Results: $( [ "$STATUS" -eq 0 ] && echo PASSED || echo FAILED )"
  BODY="Luma Selenium Test Results\n\nStatus: $( [ "$STATUS" -eq 0 ] && echo PASSED || echo FAILED )\n\nSee container logs for details."
  curl --url "smtp://mailhog:1025" \
    --mail-from "$EMAIL_SENDER" \
    --mail-rcpt "$EMAIL_RECIPIENT" \
    -T <(echo -e "Subject: $SUBJECT\n\n$BODY")
fi

exit "$STATUS"

