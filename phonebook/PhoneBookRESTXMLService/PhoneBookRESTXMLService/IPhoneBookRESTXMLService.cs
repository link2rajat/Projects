using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.ServiceModel;
using System.ServiceModel.Web;
using System.Text;

namespace PhoneBookRESTXMLService
{
   [ServiceContract]
   public interface IPhoneBookRESTXMLService
   {
        // add an entry to the phone book database
        [OperationContract]
        [WebGet(UriTemplate = "/AddEntry/{lastName}/{firstName}/{phoneNumber}")]
        void AddEntry( string lastName, string firstName, string phoneNumber);

        // retrieve phone book entries with a given last name
        [OperationContract]
        [WebGet(UriTemplate = "/GetEntry/{lastName}")]
        PhoneBookEntry[]  GetEntry(string lastname);
   } // end interface IPhoneBookRESTXMLService
}

