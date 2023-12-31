/*
 * Jitsi, the OpenSource Java VoIP and Instant Messaging client.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.java.sip.communicator.service.contactlist

import net.java.sip.communicator.service.contactlist.event.MetaContactListListener
import net.java.sip.communicator.service.protocol.Contact
import net.java.sip.communicator.service.protocol.ContactGroup
import net.java.sip.communicator.service.protocol.ProtocolProviderService

/**
 * The `MetaContactListService` handles the global project contact
 * list including contacts from all implemented protocols.
 *
 * An implementation of the `MetaContactListService` would take care
 * of synchronizing the local copy of the contact list with the  versions stored
 * on the various server protocols.
 *
 * All modules that would for some reason like to query or modify the contact
 * list should use this service rather than accessing protocol providers
 * directly.
 *
 * The point of `MetaContact`s is being able to merge different
 * protocol specific contacts so that they represent a single person or identity.
 * Every protocol specific `Contact` would therefore automatically
 * be assigned to a corresponding `MetaContact`. A single
 * MetaContact may containing multiple contacts (e.g. a single person often
 * has accounts in different protocols) while a single protocol specific
 * Contact may only be assigned to a exactly one MetaContact.
 *
 * Once created a MetaContact may be updated to contain multiple protocol
 * specific contacts. These protocol specific contacts may also be removed
 * away from a MetaContact. Whenever a MetaContact remains empty (i.e. all of
 * its protocol specific contacts are removed) it is automatically deleted.
 *
 * Note that for most of the methods defined by this interface, it is likely
 * that implementations require one or more network operations to complete
 * before returning. It is therefore strongly advised not to call these methods
 * in event dispatching threads (watch out UI implementors ;) ) as this may lead
 * to unpleasant user experience.
 *
 * The MetaContactListService also defines a property named:<br></br>
 * `net.java.sip.communicator.service.contactlist.PROVIDER_MASK`<br></br>
 * When this property is set, implementations of the MetaContactListService
 * would only interact with protocol providers that same property set to the
 * same value. This feature is mostly used during unit testing so that testing
 * bundles could make sure that a tested meta contact list implementation would
 * only load their mocking protocol provider implementations during the test
 * run.
 *
 *
 * @author Emil Ivov
 * @author Eng Chong Meng
 */
interface MetaContactListService {
    /**
     * Returns the root `MetaContactGroup` in this contact list.
     * All meta contacts and subgroups are children of the root meta contact
     * and references to them can only be obtained through it.
     *
     * @return the root `MetaContactGroup` for this contact list.
     */
    fun getRoot(): MetaContactGroup

    /**
     * Returns the meta contact group that is a direct parent of the specified `child`.
     *
     * @param child the `MetaContactGroup` whose parent group we're
     * looking for. If no parent is found `null` is returned.
     * @return the `MetaContactGroup` that contains `child` or
     * null if no parent couldn't be found.
     */
    fun findParentMetaContactGroup(child: MetaContactGroup?): MetaContactGroup?

    /**
     * Returns the meta contact group that is a direct parent of the specified
     * `child`. If no parent is found `null` is returned.
     *
     * @param child the `MetaContact` whose parent group we're looking for.
     * @return the `MetaContactGroup` that contains `child` or
     * null if no such group could be found.
     */
    fun findParentMetaContactGroup(child: MetaContact?): MetaContactGroup?

    /**
     * Returns the MetaContact containing the specified contact or null if no
     * such MetaContact was found. The method can be used when for example
     * we need to find the MetaContact that is the author of an incoming message
     * and the corresponding ProtocolProviderService has only provided a
     * `Contact` as its author.
     *
     * @param contact the contact whose encapsulating meta contact we're looking for.
     * @return the MetaContact containing the specified contact or `null`
     * if no such contact is present in this contact list.
     */
    fun findMetaContactByContact(contact: Contact?): MetaContact?

    /**
     * Returns the MetaContactGroup encapsulating the specified protocol contact
     * group or null if no such MetaContactGroup was found.
     *
     * @param group the group whose encapsulating meta group we're looking for.
     * @return the MetaContact containing the specified contact or `null`
     * if no such contact is present in this contact list.
     */
    fun findMetaContactGroupByContactGroup(group: ContactGroup?): MetaContactGroup?

    /**
     * Returns the MetaContact that corresponds to the specified metaContactID.
     *
     * @param metaContactID a String identifier of a meta contact.
     * @return the MetaContact with the specified string identifier or
     * `null` if no such meta contact was found.
     */
    fun findMetaContactByMetaUID(metaContactID: String?): MetaContact?

    /**
     * Returns the MetaContactGroup that corresponds to the specified metaGroupID.
     *
     * @param metaGroupID a String identifier of a meta contact group.
     * @return the MetaContactGroup with the specified string identifier or null
     * if no such meta contact was found.
     */
    fun findMetaContactGroupByMetaUID(metaGroupID: String?): MetaContactGroup?

    /**
     * Returns a list of all `MetaContact`s containing a protocol contact
     * from the given `ProtocolProviderService`.
     *
     * @param protocolProvider the `ProtocolProviderService` whose contacts we're looking for.
     * @return a list of all `MetaContact`s containing a protocol contact
     * from the given `ProtocolProviderService`.
     */
    fun findAllMetaContactsForProvider(protocolProvider: ProtocolProviderService?): Iterator<MetaContact?>

    /**
     * Returns a list of all `MetaContact`s contained in the given group
     * and containing a protocol contact from the given `ProtocolProviderService`.
     *
     * @param protocolProvider the `ProtocolProviderService` whose contacts we're looking for.
     * @param metaContactGroup the parent group.
     * @return a list of all `MetaContact`s containing a protocol contact
     * from the given `ProtocolProviderService`.
     */
    fun findAllMetaContactsForProvider(
            protocolProvider: ProtocolProviderService?, metaContactGroup: MetaContactGroup?): Iterator<MetaContact?>

    /**
     * Returns a list of all `MetaContact`s containing a protocol contact
     * corresponding to the given `contactAddress` string.
     *
     * @param contactAddress the contact address for which we're looking for a parent `MetaContact`.
     * @return a list of all `MetaContact`s containing a protocol contact
     * corresponding to the given `contactAddress` string.
     */
    fun findAllMetaContactsForAddress(contactAddress: String?): Iterator<MetaContact?>?

    /**
     * Adds a listener for `MetaContactListChangeEvent`s posted after the tree changes.
     *
     * @param l the listener to add
     */
    fun addMetaContactListListener(l: MetaContactListListener?)

    /**
     * Removes a listener previously added with `addContactListListener`.
     *
     * @param l the listener to remove
     */
    fun removeMetaContactListListener(l: MetaContactListListener?)

    /**
     * Makes the specified `contact` a child of the
     * `newParent` MetaContact. If `contact` was
     * previously a child of another meta contact, it will be removed from its
     * old parent before being added to the new one. If the specified contact
     * was the only child of its previous parent, then it (the previous parent)
     * will be removed.
     *
     * @param contact the `Contact` to move to the
     * @param newParent the MetaContact where we'd like contact to be moved.
     * @throws MetaContactListException with an appropriate code if the
     * operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun moveContact(contact: Contact?, newParent: MetaContact?)

    /**
     * Makes the specified `contact` a child of the
     * `newParent` MetaContactGroup. If `contact` was
     * previously a child of a meta contact, it will be removed from its
     * old parent and to a newly created one even if they both are in the same
     * group. If the specified contact was the only child of its previous
     * parent, then the meta contact will also be moved.
     *
     * @param contact the `Contact` to move to the
     * @param newParent the MetaContactGroup where we'd like contact to be moved.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun moveContact(contact: Contact?, newParent: MetaContactGroup?)

    /**
     * Deletes the specified contact from both the local contact list and (if
     * applicable) the server stored contact list if supported by the
     * corresponding protocol. If the `MetaContact` that contained
     * the given contact had no other children, it will be removed.
     *
     * @param contact the contact to remove.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun removeContact(contact: Contact?)

    /**
     * First makes the specified protocol provider create the contact as
     * indicated by `contactID`, and then associates it to the
     * _existing_ `metaContact` given as an argument.
     *
     * @param provider the ProtocolProviderService that should create the
     * contact indicated by `contactID`.
     * @param metaContact the meta contact where that the newly created contact should be associated to.
     * @param contactID the identifier of the contact that the specified provider
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun addNewContactToMetaContact(provider: ProtocolProviderService?, metaContact: MetaContact?, contactID: String?)

    /**
     * First makes the specified protocol provider create a contact
     * corresponding to the specified `contactID`, then creates a new
     * MetaContact which will encapsulate the newly-created protocol specific
     * contact. Depending on implementations the method may sometimes need
     * time to complete as it may be necessary for an underlying protocol to
     * wait for a server to acknowledge addition of the contact.
     *
     * If the specified parent MetaContactGroup did not have a corresponding
     * group on the protocol server, it will be created before the contact itself.
     *
     * @param provider a ref to `ProtocolProviderService` instance
     * which will create the actual protocol specific contact.
     * @param metaContactGroup the MetaContactGroup where the newly created meta
     * contact should be stored.
     * @param contactID a protocol specific string identifier indicating the
     * contact the protocol provider should create.
     * @return the newly created `MetaContact`
     * @throws MetaContactListException with an appropriate code if the
     * operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun createMetaContact(provider: ProtocolProviderService?, metaContactGroup: MetaContactGroup?, contactID: String?): MetaContact

    /**
     * Moves the specified `MetaContact` to `newGroup`.
     *
     * @param metaContact the `MetaContact` to move.
     * @param newGroup the `MetaContactGroup` that should be the new parent of `contact`.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun moveMetaContact(metaContact: MetaContact?, newGroup: MetaContactGroup?)

    /**
     * Sets the display name for `metaContact` to be `newName`.
     *
     * @param metaContact the `MetaContact` that we are renaming
     * @param newName a `String` containing the new display name for `metaContact`.
     * @throws IllegalArgumentException if `metaContact` is not an
     * instance that belongs to the underlying implementation.
     */
    @Throws(IllegalArgumentException::class)
    fun renameMetaContact(metaContact: MetaContact?, newName: String?)

    /**
     * Resets display name of the MetaContact to show the value from the underlying contacts.
     *
     * @param metaContact the `MetaContact` that we are operating on
     * @throws IllegalArgumentException if `metaContact` is not an
     * instance that belongs to the underlying implementation.
     */
    @Throws(IllegalArgumentException::class)
    fun clearUserDefinedDisplayName(metaContact: MetaContact?)

    /**
     * Removes the specified `metaContact` as well as all of its underlying contacts.
     *
     * @param metaContact the metaContact to remove.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun removeMetaContact(metaContact: MetaContact?)

    /**
     * Creates a `MetaContactGroup` with the specified group name.
     * Initially, the group would only be created locally. Corresponding
     * server stored groups will be created on the fly, whenever real protocol
     * specific contacts are added to the group if the protocol lying behind them supports that.
     *
     *
     * @param parentGroup the `MetaContactGroup` that should be the parent of the newly created group.
     * @param groupName the name of the `MetaContactGroup` to create.
     * @return the newly created `MetaContactGroup`
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun createMetaContactGroup(parentGroup: MetaContactGroup?, groupName: String?): MetaContactGroup

    /**
     * Renames the specified `MetaContactGroup` as indicated by the `newName` param.
     * The operation would only affect the local meta group and would not
     * "touch" any encapsulated protocol specific group.
     *
     *
     * @param group the `MetaContactGroup` to rename.
     * @param newGroupName the new name of the `MetaContactGroup` to rename.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun renameMetaContactGroup(group: MetaContactGroup?, newGroupName: String?)

    /**
     * Removes the specified meta contact group, all its corresponding protocol
     * specific groups and all their children. If some of the children belong to
     * server stored contact lists, they will be updated to not include the child contacts any more.
     *
     * @param groupToRemove the `MetaContactGroup` to have removed.
     * @throws MetaContactListException with an appropriate code if the operation fails for some reason.
     */
    @Throws(MetaContactListException::class)
    fun removeMetaContactGroup(groupToRemove: MetaContactGroup?)

    /**
     * Removes local resources storing copies of the meta contact list. This
     * method is meant primarily to aid automated testing which may depend on
     * beginning the tests with an empty local contact list.
     */
    fun purgeLocallyStoredContactListCopy()

    companion object {
        /**
         * This property is used to tell implementations of the
         * MetaContactListService that they are to only interact with providers
         * that have the same property set to the same value as the system one.
         * This feature is mostly used during unit testing so that testing bundles
         * could make sure that a tested meta contact list implementation would only
         * load their mocking protocol provider implementations during the test run.
         */
        const val PROVIDER_MASK_PROPERTY = "contactlist.PROVIDER_MASK"
    }
}