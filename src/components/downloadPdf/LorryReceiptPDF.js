import React from "react";
import icon from "./../../images/icon2.jpg";

const LorryReceiptPDF = ({lrDetails}) => {
  return (
    <>
      {lrDetails ? (
        <div style={{fontSize: 12, margin: 15}}>
          <div style={{height: 15}}>&nbsp;</div>
          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1}}>
              <img style={{maxHeight: 45}} src={icon} />
            </div>

            <div style={{flex: 3.5}}>
              <span style={{fontSize: 14, fontWeight: 600}}>
                Tanvi Lokhande Transport
              </span>
              <div>{lrDetails.transporter.address}</div>
            </div>

            <div style={{flex: 2, textAlign: "end"}}>
              <div>
                <span style={{fontWeight: 600}}>Mob No: </span>
                {lrDetails.transporter.mobileNumber}
              </div>
              <div>
                <span style={{fontWeight: 600}}>LR No: </span>
                {lrDetails.receiptNumber}
              </div>
            </div>
          </div>

          <div style={{textAlign: "end"}}>
            <span style={{fontWeight: 600}}>VC ID: </span>
            {lrDetails.vcId}
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
            <div style={{flex: 1}}>{lrDetails.driver.name}</div>
            <div style={{flex: 3, textAlign: "end"}}>
              <span style={{fontWeight: 600}}>Vehicle Number: </span>
              {lrDetails.vehicleNumber}
            </div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1.5, fontWeight: 600}}>Place of Loading:</div>
            <div style={{flex: 4.5}}>{lrDetails.loadingAddress}</div>
          </div>

          <div style={{display: "flex", flexDirection: "row"}}>
            <div style={{flex: 1.5, fontWeight: 600}}>Destination:</div>
            <div style={{flex: 4.5}}>{lrDetails.unloadingAddress}</div>
          </div>
          <div style={{display: "flex", flexDirection: "row", marginTop: 15}}>
            <table>
              <thead>
                <tr>
                  <th className="lr-pdf-th">Quantity</th>
                  <th className="lr-pdf-th">Weight</th>
                  <th className="lr-pdf-th">Description</th>
                  <th className="lr-pdf-th"></th>
                  <th className="lr-pdf-th">Freight charges</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td className="lr-pdf-th" rowSpan="3">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.quantity}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>
                  <td className="lr-pdf-th" rowSpan="3">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.weight}
                      </div>
                    ))}
                    <div>Total : {lrDetails.totalWeight}</div>
                  </td>
                  <td className="lr-pdf-th" rowSpan="3">
                    {lrDetails.consignments.map((c, i) => (
                      <div key={i} style={{marginBottom: 10}}>
                        {c.description}
                      </div>
                    ))}
                    <div>&nbsp;</div>
                  </td>

                  <td className="lr-pdf-th">Freight</td>
                  <td className="lr-pdf-th">
                    {lrDetails.freightCharge.freight}
                  </td>
                </tr>
                <tr>
                  <td className="lr-pdf-th">Advance</td>
                  <td className="lr-pdf-th">
                    {lrDetails.freightCharge.advance}
                  </td>
                </tr>
                <tr>
                  <td className="lr-pdf-th">To Pay/Paid</td>
                  <td className="lr-pdf-th">{lrDetails.freightCharge.toPay}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div style={{height: 15}}>&nbsp;</div>
        </div>
      ) : null}
    </>
  );
};

export default LorryReceiptPDF;
