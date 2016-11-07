<%@ Page Language="C#" Async="true" AutoEventWireup="true" CodeBehind="PhoneBookClient.aspx.cs" Inherits="PhoneBookRESTXMLClient.PhoneBookClient" EnableSessionState="True" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title></title>
    <style type="text/css">
       .auto-style1 {
          height: 26px;
          width: 99px;
       }
       .auto-style2 {
          width: 99px;
       }
       .auto-style3 {
          width: 100px;
          height: 26px;
       }
       </style>
</head>
<body>
    <form id="form1" runat="server">
    <div>
       <asp:Label ID="titleLabel" runat="server" Font-Bold="True" 
          Font-Names="sans-serif" Font-Size="XX-Large" Text="Phone Book"></asp:Label>
       <br />
       <br />
       <asp:Label ID="addEntryLabel" runat="server" 
          Text="Add an entry to the phone book:"></asp:Label>
       &nbsp;
       <table>
          <tr>
             <td style="width: 100px; height: 26px;">
                <asp:Label ID="lastLabel" runat="server" Text="Last name:"></asp:Label>
             </td>
             <td class="auto-style1">
                <asp:TextBox ID="lastTextBox" runat="server">
                     </asp:TextBox>
             </td>
          </tr>
          <tr>
             <td style="width: 100px">
                <asp:Label ID="firstLabel" runat="server" Text="First name:"></asp:Label>
             </td>
             <td class="auto-style2">
                <asp:TextBox ID="firstTextBox" runat="server">
                     </asp:TextBox>
             </td>
          </tr>
          <tr>
             <td class="auto-style3">
                <asp:Label ID="phoneLabel" runat="server" Text="Phone number:"></asp:Label>
             </td>
             <td class="auto-style1">
                <asp:TextBox ID="phoneTextBox" runat="server">
                     </asp:TextBox>
             </td>
          </tr>
       </table>
    </div>
    <asp:Button ID="addButton" runat="server"
       Text="Add Phone Book Entry" Width="266px" />
    <br />
    <br />
    <asp:Label ID="findLabel" runat="server" 
       Text="Locate entries in the phone book:"></asp:Label>
    <br />
    <table>
       <tr>
          <td style="width: 100px">
             <asp:Label ID="findLastLabel" runat="server" Text="Last name:"></asp:Label>
          </td>
          <td style="width: 100px">
             <asp:TextBox ID="findLastTextBox" runat="server">
                  </asp:TextBox>
          </td>
       </tr>
    </table>
       <asp:Button ID="findLastButton" runat="server"
       Text="Find Entries by Last Name" Width="266px" />
    <br />
    <br />
    <asp:Label ID="resultsLabel" runat="server" Text="Results:">
       </asp:Label>
    <br />
    <asp:TextBox ID="resultsTextBox" runat="server" Height="122px" ReadOnly="True" 
       TextMode="MultiLine" Width="385px">
       </asp:TextBox>
    </form>
</body>
</html>
