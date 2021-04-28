import React from "react";
import icon from "./../../images/icon2.jpg";

const LorryReceipt = ({lrDetails}) => {
  return (
    <>
      {lrDetails ? (
        <div className="lrDetails-content">
          <div className="lrDetails-header">
            <div style={{flex: 1}}>
              <img style={{maxHeight: 75}} src={icon} />
            </div>

            <div className="lrDetails-company">
              {lrDetails.transporter.fullName}
              <div className="lrDetails-address">
                {lrDetails.transporter.address}
              </div>
            </div>

            <div style={{flex: 1, textAlign: "end"}}>
              <div>
                <span style={{fontWeight: 600}}>Mob No:</span>{" "}
                {lrDetails.transporter.mobileNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>LR No: </span>
                {lrDetails.lorryReceipt.receiptNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>VC ID: </span>
                {lrDetails.vcId.replace("claimId:", "")}
              </div>
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignor:</div>
            <div style={{flex: 3}}>{lrDetails.consigner.fullName}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Date:</span>{" "}
              {lrDetails.lorryReceipt.createdAt}
            </div>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignee:</div>
            <div style={{flex: 3}}>{lrDetails.consignee.fullName}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Status: </span>
              {lrDetails.lorryReceipt.status}
            </div>
          </div>

          <div
            style={{
              display: "flex",
              flexDirection: "row",
            }}
          >
            <div style={{flex: 1, fontWeight: 600}}>Place of Loading:</div>
            <div style={{flex: 5}}>{lrDetails.consigner.address}</div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1, fontWeight: 600}}>Destination:</div>
            <div style={{flex: 5}}>{lrDetails.consignee.address}</div>
          </div>
          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <table>
              <tr>
                <th className="lrDetails-th">Quantity</th>
                <th className="lrDetails-th">Weight</th>
                <th className="lrDetails-th">Description</th>
                <th className="lrDetails-th"></th>
                <th className="lrDetails-th">Freight charges</th>
              </tr>
              <tr>
                <td rowSpan="3" className="lr-td-quantity">
                  1
                </td>
                <td rowSpan="3" className="lr-td-quantity">
                  200kg
                </td>
                <td rowSpan="3" className="lr-td-description">
                  Description of Good
                </td>
                <td className="lr-td-fright">Freight</td>
                <td className="lr-td-fright">500</td>
              </tr>
              <tr>
                <td className="lr-td-fright">Advance</td>
                <td className="lr-td-fright">200</td>
              </tr>
              <tr>
                <td className="lr-td-fright">To Pay/Paid</td>
                <td className="lr-td-fright">Pay : 300</td>
              </tr>
            </table>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}></div>
        </div>
      ) : null}
    </>
  );
};

export default LorryReceipt;
