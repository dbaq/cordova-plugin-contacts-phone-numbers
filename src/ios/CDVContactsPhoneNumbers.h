/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */

#import <Foundation/Foundation.h>
#import <AddressBook/ABAddressBook.h>
#import <AddressBookUI/AddressBookUI.h>
#import <ContactsUI/ContactsUI.h>
#import <Cordova/CDVPlugin.h>

@interface CDVContactsPhoneNumbers : CDVPlugin
{
    ABAddressBookRef addressBook;
}

/*
 * list
 *
 * arguments:
 *  1: successcallback - this is the javascript function that will be called with the array of found contacts
 *  2:  errorCallback - optional javascript function to be called in the event of an error
 */
- (void)list:(CDVInvokedUrlCommand*)command;
- (void)add:(CDVInvokedUrlCommand*)command;

@end

typedef void (^ CDVAddressBookWorkerBlock)(
    ABAddressBookRef         addrBook
    );
@interface CDVAddressBookPhoneNumberHelper : NSObject
{}

- (void)createAddressBook:(CDVAddressBookWorkerBlock)workerBlock;
@end
