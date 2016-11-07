using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace PhoneBookRESTJSONService
{
   public class PhoneBookRESTJSONService : IPhoneBookRESTJSONService
   {
      // create a dbcontext object to access PhoneBook database
      private PhoneBookEntities dbcontext = new PhoneBookEntities();

      // add an entry to the phone book database
      public void AddEntry(string lastName, string firstName,
         string phoneNumber)
      {
            // create PhoneBook entry to be inserted in database
            PhoneBook phnbook = new PhoneBook();



            phnbook.LastName = lastName;
            phnbook.FirstName = firstName;
            phnbook.PhoneNumber = phoneNumber;

            // insert PhoneBook entry in database
            dbcontext.PhoneBooks.Add(phnbook);
            dbcontext.SaveChanges();
        } // end method AddEntry

      // retrieve phone book entries with a given last name
      public PhoneBookEntry[] GetEntries(string lastName)
      {
            PhoneBookEntry[] query = (from d in dbcontext.PhoneBooks
                                      where d.LastName == lastName
                                      select new PhoneBookEntry
                                      {
                                          LastName = d.LastName,
                                          FirstName = d.FirstName,
                                          PhoneNumber = d.PhoneNumber
                                      }).ToArray();

            return query;

        } // end method GetEntries
   }
}

