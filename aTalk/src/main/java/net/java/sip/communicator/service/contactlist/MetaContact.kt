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

import net.java.sip.communicator.service.protocol.Contact
import net.java.sip.communicator.service.protocol.OperationSet
import net.java.sip.communicator.service.protocol.ProtocolProviderService
import org.json.JSONArray
import org.json.JSONObject

/**
 * A MetaContact is an abstraction used for merging multiple Contacts (most
 * often) belonging to different `ProtocolProvider`s.
 *
 *
 * Instances of a MetaContact are read-only objects that cannot be modified
 * directly but only through the corresponding MetaContactListService.
 *
 *
 * @author Emil Ivov
 * @author Lubomir Marinov
 * @author Eng Chong Meng
 */
interface MetaContact : Comparable<MetaContact?> {
    /**
     * Returns the default protocol specific `Contact` to use when
     * communicating with this `MetaContact`.
     *
     * @return the default `Contact` to use when communicating with this `MetaContact`
     */
    fun getDefaultContact(): Contact

    /**
     * Returns the default protocol specific `Contact` to use with this
     * `MetaContact` for a precise operation (IM, call, ...).
     *
     * @param operationSet the operation for which the default contact is needed
     * @return the default contact for the specified operation.
     */
    fun getDefaultContact(operationSet: Class<out OperationSet?>?): Contact?

    /**
     * Returns the first found protocol specific `Contact` of the `MetaContact`
     * that support a precise operation (IM, call, ...).
     * Currently the main use is to display the call button for user access
     *
     * @param operationSet the operation for which the metacontact is supported
     * @return the first contact for the specified operation supported.
     */
    fun getOpSetSupportedContact(operationSet: Class<out OperationSet?>?): Contact?
    fun isFeatureSupported(feature: String?): Boolean

    /**
     * Returns a `java.util.Iterator` with all protocol specific
     * `Contacts` encapsulated by this `MetaContact`.
     *
     *
     * Note to implementors:  In order to prevent problems with concurrency, the
     * `Iterator` returned by this method should not be over the actual
     * list of contacts but rather over a copy of that list.
     *
     *
     *
     * @return a `java.util.Iterator` containing all protocol specific
     * `Contact`s that were registered as subContacts for this `MetaContact`
     */
    fun getContacts(): Iterator<Contact?>

    /**
     * Returns a contact encapsulated by this meta contact, having the specified
     * contactAddress and coming from the indicated ownerProvider.
     *
     * @param contactAddress the address of the contact who we're looking for.
     * @param ownerProvider a reference to the ProtocolProviderService that
     * the contact we're looking for belongs to.
     * @return a reference to a `Contact`, encapsulated by this
     * MetaContact, carrying the specified address and originating from the
     * specified ownerProvider or null if no such contact exists..
     */
    fun getContact(contactAddress: String?, ownerProvider: ProtocolProviderService?): Contact?

    /**
     * Returns `true` if the given `protocolContact` is contained
     * in this `MetaContact`, otherwise - returns `false`.
     *
     * @param protocolContact the `Contact` we're looking for
     * @return `true` if the given `protocolContact` is contained
     * in this `MetaContact`, otherwise - returns `false`
     */
    fun containsContact(protocolContact: Contact?): Boolean

    /**
     * Returns the number of protocol specific `Contact`s that this `MetaContact` contains.
     *
     * @return an int indicating the number of protocol specific contacts merged
     * in this `MetaContact`
     */
    fun getContactCount(): Int

    /**
     * Returns all protocol specific Contacts, encapsulated by this MetaContact
     * and coming from the indicated ProtocolProviderService. If none of the
     * contacts encapsulated by this MetaContact is originating from the
     * specified provider then an empty iterator is returned.
     *
     *
     * Note to implementors:  In order to prevent problems with concurrency, the
     * `Iterator` returned by this method should not be over the actual
     * list of contacts but rather over a copy of that list.
     *
     *
     *
     * @param provider a reference to the `ProtocolProviderService`
     * whose contacts we'd like to get.
     * @return an `Iterator` over all contacts encapsulated in this
     * `MetaContact` and originating from the specified provider.
     */
    fun getContactsForProvider(provider: ProtocolProviderService?): Iterator<Contact?>?

    /**
     * Returns all protocol specific Contacts, encapsulated by this MetaContact
     * and supporting the given `opSetClass`. If none of the
     * contacts encapsulated by this MetaContact is supporting the specified
     * `OperationSet` class then an empty list is returned.
     *
     *
     * Note to implementors:  In order to prevent problems with concurrency, the
     * `List` returned by this method should not be the actual list of
     * contacts but rather a copy of that list.
     *
     *
     *
     * @param opSetClass the operation for which the default contact is needed
     * @return a `List` of all contacts encapsulated in this
     * `MetaContact` and supporting the specified `OperationSet`
     */
    fun getContactsForOperationSet(opSetClass: Class<out OperationSet?>?): List<Contact?>?

    /**
     * Returns the MetaContactGroup currently containing this meta contact
     *
     * @return a reference to the MetaContactGroup currently containing this meta contact.
     */
    fun getParentMetaContactGroup(): MetaContactGroup?

    /**
     * Returns a String identifier (the actual contents is left to implementations)
     * that uniquely represents this `MetaContact` in the containing `MetaContactList`
     *
     * @return String
     */
    fun getMetaUID(): String

    /**
     * Set the unread message count for this metaCount represent
     *
     * @param count unread message count
     */
    fun setUnreadCount(count: Int)

    /**
     * Returns the unread message count for this metaContact
     *
     * @return the unread message count
     */
    fun getUnreadCount(): Int

    /**
     * Returns a characteristic display name that can be used when including
     * this `MetaContact` in user interface.
     *
     * @return a human readable String that represents this meta contact.
     */
    fun getDisplayName(): String?

    /**
     * Returns the avatar of this contact, that can be used when including this
     * `MetaContact` in user interface.
     *
     * @return an avatar (e.g. user photo) of this contact.
     */
    fun getAvatar(): ByteArray?

    /**
     * Returns the avatar of this contact, that can be used when including this
     * `MetaContact` in user interface. The isLazy
     * parameter would tell the implementation if it could return the locally
     * stored avatar or it should obtain the avatar right from the server.
     *
     * @param isLazy Indicates if this method should return the locally stored
     * avatar or it should obtain the avatar right from the server.
     * @return an avatar (e.g. user photo) of this contact.
     */
    fun getAvatar(isLazy: Boolean): ByteArray?

    /**
     * Returns a String representation of this `MetaContact`.
     *
     * @return a String representation of this `MetaContact`.
     */
    override fun toString(): String

    /**
     * Adds a custom detail to this contact.
     *
     * @param name name of the detail.
     * @param value the value of the detail.
     */
    fun addDetail(name: String, value: String)

    /**
     * Remove the given detail.
     *
     * @param name of the detail to be removed.
     * @param value value of the detail to be removed.
     */
    fun removeDetail(name: String, value: String?)

    /**
     * Remove all details with given name.
     *
     * @param name of the details to be removed.
     */
    fun removeDetails(name: String)

    /**
     * Change the detail.
     *
     * @param name of the detail to be changed.
     * @param oldValue the old value of the detail.
     * @param newValue the new value of the detail.
     */
    fun changeDetail(name: String, oldValue: String, newValue: String)

    /**
     * Get all details with given name.
     *
     * @param name the name of the details we are searching.
     * @return JSONArray for the details with the given name.
     */
    fun getDetails(name: String): JSONArray
    fun getDetails(): JSONObject

    /**
     * Gets the user data associated with this instance and a specific key.
     *
     * @param key the key of the user data associated with this instance to be retrieved
     * @return an `Object` which represents the value associated with
     * this instance and the specified `key`; `null` if no
     * association with the specified `key` exists in this instance
     */
    fun getData(key: Any?): Any?

    /**
     * Sets a user-specific association in this instance in the form of a
     * key-value pair. If the specified `key` is already associated in
     * this instance with a value, the existing value is overwritten with the
     * specified `value`.
     *
     *
     * The user-defined association created by this method and stored in this
     * instance is not serialized by this instance and is thus only meant for runtime use.
     *
     *
     *
     * The storage of the user data is implementation-specific and is thus not
     * guaranteed to be optimized for execution time and memory use.
     *
     *
     * @param key the key to associate in this instance with the specified value
     * @param value the value to be associated in this instance with the specified `key`
     */
    fun setData(key: Any?, value: Any?)
}