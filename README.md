# ContactsPhoneNumbers

Cross-platform plugin for Cordova / PhoneGap to list all the contacts with at least a phone number.

## Installing the plugin ##
```
   cordova plugin add https://github.com/dbaq/cordova-plugin-contacts-phone-numbers.git
```
## Using the plugin ##
The plugin creates the object `navigator.contactsPhoneNumbers` with the methods

  `list(success, fail)`

A full example could be:

```
   //
   //
   // after deviceready
   //
   //
   navigator.contactsPhoneNumbers.list(function(contacts) {
      console.log(contacts.length + ' contacts found');
      for(var i = 0; i < contacts.length; i++) {
         console.log(contacts[i].id + " - " + contacts[i].displayName);
         for(var j = 0; j < contacts[i].phoneNumbers.length; j++) {
            var phone = contacts[i].phoneNumbers[j];
            console.log("===> " + phone.type + "  " + phone.number + " (" + phone.normalizedNumber+ ")"); 
         }
      }
   }, function(error) {
      console.error(error);
   });

```

## Behaviour

The plugin retrieves **ONLY** the contacts containing one or more phone numbers. It does not allow to modify them (use [the official cordova contacts plugin for that](https://github.com/apache/cordova-plugin-contacts)).

It is difficult and inefficient to retrieve the list of all the contacts with at least a phone number with the official plugin. I needed a fastest way to retrieve a simple list containing the name and the list of phone numbers.

If you need more fields like the email address, open an issue and I'll see what I can do.


## iOS and Android

The plugin works with iOS **(TO BE DONE)** and Android

## Contributing

Thanks for considering contributing to this project.

### Finding something to do

Ask, or pick an issue and comment on it announcing your desire to work on it. Ideally wait until we assign it to you to minimize work duplication.

### Reporting an issue

- Search existing issues before raising a new one.

- Include as much detail as possible.

### Pull requests

- Make it clear in the issue tracker what you are working on, so that someone else doesn't duplicate the work.

- Use a feature branch, not master.

- Rebase your feature branch onto origin/master before raising the PR.

- Keep up to date with changes in master so your PR is easy to merge.

- Be descriptive in your PR message: what is it for, why is it needed, etc.

- Make sure the tests pass

- Squash related commits as much as possible.

### Coding style

- Try to match the existing indent style.

- Don't mix platform-specific stuff into the main code.



## Licence ##

The MIT License

Copyright (c) 2013 Didier Baquier

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
