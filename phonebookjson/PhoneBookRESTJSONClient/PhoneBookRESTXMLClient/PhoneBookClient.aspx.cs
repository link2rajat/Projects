using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Runtime.Serialization.Json;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Xml.Linq;

namespace PhoneBookRESTXMLClient
{
   public partial class PhoneBookClient : System.Web.UI.Page
   {
      private const string OPERATION = "operation";
      private const string ADD_ENTRY = "AddEntry";
      private const string GET_ENTRIES = "GetEntry";

      // create an object to invoke to PhoneBookService
      private HttpClient client = new HttpClient();

      // handle page load events
      protected async void Page_Load(object sender, EventArgs e)
      {
         if (IsPostBack)
         {
            // send request to PhoneBookRESTXMLService if fields are filled
            if ((!string.IsNullOrEmpty(firstTextBox.Text)) &&
               (!string.IsNullOrEmpty(lastTextBox.Text)) &&
               (!string.IsNullOrEmpty(phoneTextBox.Text)))
            {
               HttpResponseMessage response =
                  await client.GetAsync(new Uri(
                     "http://localhost:52163/PhoneBookRESTJSONService.svc/AddEntry/" +
                     lastTextBox.Text.Trim() + "/" + firstTextBox.Text.Trim() + "/" + 
                     phoneTextBox.Text.Trim()));

               clearFields();

               if (response.StatusCode == System.Net.HttpStatusCode.OK)
                  resultsTextBox.Text = "Entry added successfully";
               else
                  resultsTextBox.Text = "AddEntry failed with HTTP code " + response.StatusCode;
            } // end if
            else if (findLastTextBox.Text != string.Empty) // send request to PhoneBookRESTXMLService if field is filled
            {
               String result = await client.GetStringAsync(new Uri(
                  "http://localhost:52163/PhoneBookRESTJSONService.svc/GetEntries/" + findLastTextBox.Text));

               // deserialize response into array of PhoneBookEntry objects
               DataContractJsonSerializer JSONSerializer =
                  new DataContractJsonSerializer(typeof(PhoneBookEntry[]));
               PhoneBookEntry[] entries = entries = 
                  (PhoneBookEntry[])(JSONSerializer.ReadObject(new MemoryStream(Encoding.Unicode.GetBytes(result))));

               clearFields();

               // if there are no phone book entries in response
               if (entries.Length == 0)
               {
                  resultsTextBox.Text = "No entries with that last name.";
               }
               else
               {
                  // print information for each phone book entry
                  foreach (var entry in entries)
                  {
                     resultsTextBox.Text += '\n' + 
                        string.Format("{0}, {1}, {2}",
                           entry.LastName,
                           entry.FirstName,
                           entry.PhoneNumber);
                  } // end foreach
               } // end else
            } // end if
         }
      }

      private void clearFields()
      {
         resultsTextBox.Text = string.Empty;
         firstTextBox.Text = string.Empty;
         lastTextBox.Text = string.Empty;
         phoneTextBox.Text = string.Empty;
         findLastTextBox.Text = string.Empty;
      }
   }

   // class representing deserialized JSON object
   [Serializable]
   public class PhoneBookEntry
   {
      public string FirstName;
      public string LastName;
      public string PhoneNumber;
   } // end Class PhoneBookEntry

}

/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 **************************************************************************/