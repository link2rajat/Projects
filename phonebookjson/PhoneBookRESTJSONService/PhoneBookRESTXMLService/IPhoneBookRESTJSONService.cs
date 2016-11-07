using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace PhoneBookRESTJSONService
{
   [ServiceContract]
   public interface IPhoneBookRESTJSONService
   {
      // add an entry to the phone book database
      [OperationContract]
      [WebGet(ResponseFormat = WebMessageFormat.Json,
            UriTemplate = "/AddEntry/{lastName}/{firstName}/{phoneNumber}")]
        void AddEntry(string lastName, string firstName, string phoneNumber);

        // retrieve phone book entries with a given last name
        [OperationContract]
      [WebGet(ResponseFormat = WebMessageFormat.Json,
            UriTemplate = "/GetEntries/{lastName}")]
        PhoneBookEntry[] GetEntries(string lastName);
    } // end interface IPhoneBookRESTXMLService
}

