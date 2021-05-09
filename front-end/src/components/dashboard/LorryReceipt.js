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
              {lrDetails.transporter.name}
              <div className="lrDetails-address">
                {lrDetails.transporter.address}
              </div>
            </div>

            <div style={{flex: 1, textAlign: "end"}}>
              <div>
                <span style={{fontWeight: 600}}>Mob No: </span>
                {lrDetails.transporter.mobileNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>LR No: </span>
                {lrDetails.receiptNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>VC ID: </span>
                {lrDetails.vcId}
              </div>
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignor:</div>
            <div style={{flex: 3}}>{lrDetails.consigner.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Date: </span>
              {new Date(lrDetails.date).getDate() +
                "/" +
                parseInt(new Date(lrDetails.date).getMonth() + 1) +
                "/" +
                new Date(lrDetails.date).getFullYear()}
            </div>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Consignee:</div>
            <div style={{flex: 3}}>{lrDetails.consignee.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Status: </span> {lrDetails.status}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 0.8, fontWeight: 600}}>Driver:</div>
            <div style={{flex: 3}}>{lrDetails.driver.name}</div>
            <div style={{flex: 1, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Vehicle Number: </span>
              {lrDetails.vehicleNumber}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1, fontWeight: 600}}>Place of Loading:</div>
            <div style={{flex: 5}}>{lrDetails.loadingAddress}</div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1, fontWeight: 600}}>Destination:</div>
            <div style={{flex: 5}}>{lrDetails.unloadingAddress}</div>
          </div>
          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <table>
              <thead>
                <tr>
                  <th className="lrDetails-th">Quantity</th>
                  <th className="lrDetails-th">Weight</th>
                  <th className="lrDetails-th">Description</th>
                  <th className="lrDetails-th"></th>
                  <th className="lrDetails-th">Freight charges</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td rowSpan="3" className="lr-td-quantity">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.quantity}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>
                  <td rowSpan="3" className="lr-td-quantity">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.weight}
                      </div>
                    ))}
                    <div>Total : {lrDetails.totalWeight}</div>
                  </td>
                  <td rowSpan="3" className="lr-td-description">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.description}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>

                  <td className="lr-td-fright">Freight</td>
                  <td className="lr-td-fright">
                    {lrDetails.freightCharge.freight}
                  </td>
                </tr>
                <tr>
                  <td className="lr-td-fright">Advance</td>
                  <td className="lr-td-fright">
                    {lrDetails.freightCharge.advance}
                  </td>
                </tr>
                <tr>
                  <td className="lr-td-fright">To Pay/Paid</td>
                  <td className="lr-td-fright">
                    {lrDetails.freightCharge.toPay}
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style={{display: "flex", flexDirection: "row"}}></div>
        </div>
      ) : null}
    </>
  );
};

export default LorryReceipt;
