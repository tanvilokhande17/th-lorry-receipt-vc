import React, { useEffect, useState } from "react";
import flow from "./../../images/Flow.png";


function PolicyDocument({ }) {

  return (
    <div style={{ flexDirection: "row" , backgroundColor: "#FFFF" }}>
      <p className="policy-document-header"><strong><u><span style={{ fontSize: "20px", lineHeight: "107%" }}>TRANSPORTHUB</span></u></strong></p>
      <p><strong><span style={{ fontSize: "16px", lineHeight: "107%", marginLeft : "20px" }}>Scenario</span></strong></p>
      <p className="p-text" style={{marginLeft: "20px"}}><strong>TransportHub</strong> is a web-based platform which is being used by the Transporters to manage the <span>consignor</span>, driver, and <span>consignee</span> details and to transport goods seamlessly without any paperwork.</p>
      <ul className="ul-text">
        <li className="p-text"><strong>Current Transportation process</strong></li>
      </ul>
      <ol className="ol-text">
        <li className="p-text">Transporter receives transportation request from Consignee.</li>
        <li className="p-text">Transporter generates lorry receipt (consignment note) which will be contract proof for <span style={{ color: "#202124" }}>consignor</span>, <span style={{ color: "#202124" }}>consignee,&nbsp;</span>and driver.</li>
        <li className="p-text">Transporter shares physical lorry receipt copy with all the participants.</li>
        <li className="p-text">During transportation, driver shares lorry receipt copy with police officer/legal authorities for verification purpose.</li>
      </ol>
      <ul className="p-text ul-text">
        <   li><strong>Drawbacks</strong></li>
      </ul>
      <ol className="ol-text">
        <li className="p-text">Original bills of lorry receipts <span style={{ fontSize: "11px", lineHeight: "107%" }}>&nbsp;</span>can be faked, lost, or tampered.</li>
        <li className="p-text">Transporters need to verify all the participants (consignor, consignee, and driver) identities.</li>
        <li className="p-text">Transporters also need to share copies of lorry receipts with all the participants.</li>
        <li className="p-text">There is no tracking of lorry receipt life cycle stages from transporter end till consignee delivery.</li>
        <li className="p-text">Receipt contains GST details of person liable for paying tax which can be digitalized.</li>
       <li className="p-text">All the information are shared including personal information as there is no mechanism to mask personal information in this process.</li>
        <li className="p-text">Verified information of Driver, Truck, Insurance & GST are missed.</li>
      </ol>
      <p style={{ marginLeft: "36.0pt" }} className="p-text">To overcome all these situations, Transporter could use <strong>TransportHub</strong> platform to generate and share verifiable credentials of Lorry receipt to individual entities. The generated LR verifiable credentials will also maintain life cycle stages. This VC can also contain participant&rsquo;s personal identification and GST invoice to represent as a digitally signed proof during verification process.</p>
      <p style={{textIndent:"36.0pt"}} className="p-text">There are 3 roles in SSI Cycle: ISSUER, VERIFIER and HOLDER, explained as below.</p>
      <p><center><span style={{flex: 5}}>
        <img style={{marginBottom : "50px", boxShadow : "0 3px 8px rgb(0 0 0 / 10%)"}} className="" src={flow} />
      </span></center></p>
    </div> 
  );
}

export default PolicyDocument;