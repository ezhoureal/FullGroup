import os
import openai

openai.organization = "org-fxJYI8WdQnGDT3Fdd4xfj9Hk"
openai.api_key = os.getenv("MY_KEY")

EVENTNAME = "441 project meeting"
EVENTTIME = "3:00 pm"
EVENTDATE = "today"

ENGINE = "text-davinci-001"
# ENGINE = "davinci-instruct-beta-v3"
MAXLENGTH = 200

with open("reminder.txt", 'r', encoding='utf-8') as f:
    reminder_prompt = f.read()

with open("char.txt", 'r', encoding='utf-8') as f:
    character_prompt = f.read()

def complete(prompt):
    response = ""
    while '\n' not in response and len(response) < MAXLENGTH:
        completion = openai.Completion.create(engine=ENGINE, prompt=prompt)
        if len(completion.choices[0].text) == 0:
            break
        prompt += completion.choices[0].text
        response += completion.choices[0].text
    return response

minit = complete(character_prompt)

print(minit)

name = minit.split()[0]
eventtext = f"{name} needs to remind the user that they have an event titled "\
    f"\"{EVENTNAME}\" at {EVENTTIME} {EVENTDATE}."
response = complete('\n\n'.join([
    reminder_prompt,
    ' '.join(minit.split()[1:]),
    eventtext,
    "Reminder:"
]))

print(response)