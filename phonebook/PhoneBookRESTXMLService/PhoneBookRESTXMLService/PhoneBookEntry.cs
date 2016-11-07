// Class that represents an entry for a contact in a phone book.
using System.Runtime.Serialization;

namespace PhoneBookRESTXMLService
{
   [DataContract]
   public class PhoneBookEntry
   {
      // property for the last name
      [DataMember]
      public string LastName { get; set; }
        [DataMember]
        public string FirstName { get; set; }
        [DataMember]
        public string PhoneNumber { get; set; }


        public PhoneBookEntry()
      {
      } // end constructor

      // return a string representation of a PhoneBookEntry
      public override string ToString()
      {
         return LastName + ", " + FirstName + ", " + PhoneNumber;
      } // end method ToString
   } // end class PhoneBookEntry
}

