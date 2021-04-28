import React, {useEffect, useState} from "react";
import {Button, Table} from "react-bootstrap";
import {useHistory} from "react-router";
import {DownloadPDF} from "./DownloadPDF";
import axios from "axios";
import {GET_LORRY_RECEIPT_URL} from "../util/Constant";
import {getUser} from "../util/User";

const Dashboard = () => {
  const user = getUser();
  const [lrList, setLRList] = useState([
    {
      lorryReceipt: {
        id: 11,
        receiptNumber: "LRN0000011",
        status: "BOOKED",
        createdAt: 1619481600000,
      },
      transporter: {
        id: 6,
        fullName: "Tanvi",
        address: "Address",
        mobileNumber: "9158447698",
        did:
          "did:elem:EiBsqCKjbe0kqT34xD1suK6TaAhZT66eY_4b6iBeSiJGpw;elem:initial-state=eyJwcm90ZWN0ZWQiOiJleUp2Y0dWeVlYUnBiMjRpT2lKamNtVmhkR1VpTENKcmFXUWlPaUlqY0hKcGJXRnllU0lzSW1Gc1p5STZJa1ZUTWpVMlN5SjkiLCJwYXlsb2FkIjoiZXlKQVkyOXVkR1Y0ZENJNkltaDBkSEJ6T2k4dmR6TnBaQzV2Y21jdmMyVmpkWEpwZEhrdmRqSWlMQ0p3ZFdKc2FXTkxaWGtpT2x0N0ltbGtJam9pSTNCeWFXMWhjbmtpTENKMWMyRm5aU0k2SW5OcFoyNXBibWNpTENKMGVYQmxJam9pVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T0NJc0luQjFZbXhwWTB0bGVVaGxlQ0k2SWpBeU9UVTVZelV3T1Rrek1XUmxZVGMyTUROa1lXWXlNRFU1WkRNMU9HSTBNMkprT1dNeVlUTm1PV0k1TVRnM1pqWm1ZalkwWTJOaFpURmhaalkzT1dNMU1TSjlMSHNpYVdRaU9pSWpjbVZqYjNabGNua2lMQ0oxYzJGblpTSTZJbkpsWTI5MlpYSjVJaXdpZEhsd1pTSTZJbE5sWTNBeU5UWnJNVlpsY21sbWFXTmhkR2x2Ymt0bGVUSXdNVGdpTENKd2RXSnNhV05MWlhsSVpYZ2lPaUl3TXpKak16TTJOV05rWW1ZMVlUY3daamRoTkdSbE5tTXhNMkUzTldKbFpqZ3dPVE5sTlRabU1qSm1NR05pWkRBeFlUUTFPV00xTW1JMU5qWmxZMlppTTJFaWZWMHNJbUYxZEdobGJuUnBZMkYwYVc5dUlqcGJJaU53Y21sdFlYSjVJbDBzSW1GemMyVnlkR2x2YmsxbGRHaHZaQ0k2V3lJamNISnBiV0Z5ZVNKZGZRIiwic2lnbmF0dXJlIjoiZU5NZ0QxMlUxRDlKSllXZzN2enhFbXFiUXdCd18zT1JDUUphQXRNOFNndzdfem9jLWNvUGlxOXpNcEttV0MwNC1ITU1NRGk1Z2p3YXBpdnNGSEh4cWcifQ",
      },
      consigner: {
        id: 7,
        fullName: "Mandar Tawde",
        address: "Mumbai",
        mobileNumber: "7303399867",
        did:
          "did:elem:EiDa1zNyURrWVm_5tNRZGP8fcGz6XheCZIXqPL_8kvvS9A;elem:initial-state=eyJwcm90ZWN0ZWQiOiJleUp2Y0dWeVlYUnBiMjRpT2lKamNtVmhkR1VpTENKcmFXUWlPaUlqY0hKcGJXRnllU0lzSW1Gc1p5STZJa1ZUTWpVMlN5SjkiLCJwYXlsb2FkIjoiZXlKQVkyOXVkR1Y0ZENJNkltaDBkSEJ6T2k4dmR6TnBaQzV2Y21jdmMyVmpkWEpwZEhrdmRqSWlMQ0p3ZFdKc2FXTkxaWGtpT2x0N0ltbGtJam9pSTNCeWFXMWhjbmtpTENKMWMyRm5aU0k2SW5OcFoyNXBibWNpTENKMGVYQmxJam9pVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T0NJc0luQjFZbXhwWTB0bGVVaGxlQ0k2SWpBeU1ERXdNMkprT0dReU9EQmxOalkyTURBeFptVXhaVFUzTlRZelpHRmpOak15TXpOaU5tWmpORGs0TldJMFpEbGtNamd3WXpVMlptSTRaV1V3TWpZeU5TSjlMSHNpYVdRaU9pSWpjbVZqYjNabGNua2lMQ0oxYzJGblpTSTZJbkpsWTI5MlpYSjVJaXdpZEhsd1pTSTZJbE5sWTNBeU5UWnJNVlpsY21sbWFXTmhkR2x2Ymt0bGVUSXdNVGdpTENKd2RXSnNhV05MWlhsSVpYZ2lPaUl3TXpGaU5UWXpZelprTXpVME5EQmlOVFJrWm1SaU5HSTNOMlpoT0Roa1pqRTJNVGczTXpBNFpqQmhObUU0WTJKbE5HSTNORFJqTVRReE5USTFZalEzTVRnaWZWMHNJbUYxZEdobGJuUnBZMkYwYVc5dUlqcGJJaU53Y21sdFlYSjVJbDBzSW1GemMyVnlkR2x2YmsxbGRHaHZaQ0k2V3lJamNISnBiV0Z5ZVNKZGZRIiwic2lnbmF0dXJlIjoiZFNNMEY1Y2lEZkxMdnVmbEdWbDY2SGdQRG5lU09iczdxSmVLSkpXS3FaTVdCaThjS2d0d29XRnV0bkl6ellkX2p6M1pTV3l4M1ZORGlCMEhoQ01MelEifQ",
      },
      consignee: {
        id: 8,
        fullName: "Mandar Tawde",
        address: "Mumbai",
        mobileNumber: "7303399868",
        did:
          "did:elem:EiB-Af3JXmGnQVXUjbsFmSGNpaSqY3HFc0zioGy-qE6rQg;elem:initial-state=eyJwcm90ZWN0ZWQiOiJleUp2Y0dWeVlYUnBiMjRpT2lKamNtVmhkR1VpTENKcmFXUWlPaUlqY0hKcGJXRnllU0lzSW1Gc1p5STZJa1ZUTWpVMlN5SjkiLCJwYXlsb2FkIjoiZXlKQVkyOXVkR1Y0ZENJNkltaDBkSEJ6T2k4dmR6TnBaQzV2Y21jdmMyVmpkWEpwZEhrdmRqSWlMQ0p3ZFdKc2FXTkxaWGtpT2x0N0ltbGtJam9pSTNCeWFXMWhjbmtpTENKMWMyRm5aU0k2SW5OcFoyNXBibWNpTENKMGVYQmxJam9pVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T0NJc0luQjFZbXhwWTB0bGVVaGxlQ0k2SWpBeVl6Y3haRGcyWkdJeE0yTTJOVGxrT0RsaU9HRmtPVEJsT0dReE1XUXdNemd5WTJObU5ERmpZakF5WVdaaE5XTmtZV0k1WkRaaVl6UXdaV1U1TlRsbFl5SjlMSHNpYVdRaU9pSWpjbVZqYjNabGNua2lMQ0oxYzJGblpTSTZJbkpsWTI5MlpYSjVJaXdpZEhsd1pTSTZJbE5sWTNBeU5UWnJNVlpsY21sbWFXTmhkR2x2Ymt0bGVUSXdNVGdpTENKd2RXSnNhV05MWlhsSVpYZ2lPaUl3TW1JeE5UVm1Zek15TURObU1ESmxaR1UwWmpZME9HWTRaamczTjJZeU9XWmtaRE5tTjJVMFpEZGpZV1EyTjJKaVpUTTVZalEyWXprMVl6Y3pZek15Wm1ZaWZWMHNJbUYxZEdobGJuUnBZMkYwYVc5dUlqcGJJaU53Y21sdFlYSjVJbDBzSW1GemMyVnlkR2x2YmsxbGRHaHZaQ0k2V3lJamNISnBiV0Z5ZVNKZGZRIiwic2lnbmF0dXJlIjoiem8wTU1VOWpXbDZ3TkNlSElRd3RvUU1QWEZVUEY3cWNqNGl6Z2Z3Uy1laHh6NU9xZGdEeHQ2a29JQU14R1lEY0dBNG9USjNEdmYzMHZ3eDZWSFp1THcifQ",
      },
      driver: {
        id: 2,
        fullName: "Mandar Tawde",
        address: "Mumbai",
        mobileNumber: "7303399866",
        did:
          "did:elem:EiBTbV0tbKV6mvuCwaUxLX0WHRsqk1_yjt1pota0C_YGWA;elem:initial-state=eyJwcm90ZWN0ZWQiOiJleUp2Y0dWeVlYUnBiMjRpT2lKamNtVmhkR1VpTENKcmFXUWlPaUlqY0hKcGJXRnllU0lzSW1Gc1p5STZJa1ZUTWpVMlN5SjkiLCJwYXlsb2FkIjoiZXlKQVkyOXVkR1Y0ZENJNkltaDBkSEJ6T2k4dmR6TnBaQzV2Y21jdmMyVmpkWEpwZEhrdmRqSWlMQ0p3ZFdKc2FXTkxaWGtpT2x0N0ltbGtJam9pSTNCeWFXMWhjbmtpTENKMWMyRm5aU0k2SW5OcFoyNXBibWNpTENKMGVYQmxJam9pVTJWamNESTFObXN4Vm1WeWFXWnBZMkYwYVc5dVMyVjVNakF4T0NJc0luQjFZbXhwWTB0bGVVaGxlQ0k2SWpBek1EUXdZV1JpT0dRMk4yTXhNV1JrWXpNd01qTXdPR0UyWldJNVlUQmlOak5oWkRkaVpqUXhaakE0T0dNek5EVXdOemszWW1VNVpEVmlNbVkxWXpKaFppSjlMSHNpYVdRaU9pSWpjbVZqYjNabGNua2lMQ0oxYzJGblpTSTZJbkpsWTI5MlpYSjVJaXdpZEhsd1pTSTZJbE5sWTNBeU5UWnJNVlpsY21sbWFXTmhkR2x2Ymt0bGVUSXdNVGdpTENKd2RXSnNhV05MWlhsSVpYZ2lPaUl3TWpObE5USmxORFUyTWpjMVpqY3pNVFE1WVRVMU1Ua3pZamRrTVdVelpHTXdNalZtTmpjeVlqTmpNemxpT1RVd1ltVmlOVFEzWkRVeE1tSXlPRFV4TmpjaWZWMHNJbUYxZEdobGJuUnBZMkYwYVc5dUlqcGJJaU53Y21sdFlYSjVJbDBzSW1GemMyVnlkR2x2YmsxbGRHaHZaQ0k2V3lJamNISnBiV0Z5ZVNKZGZRIiwic2lnbmF0dXJlIjoiRnpuY2lnSWY0aml5dlRvVllfQnQxWkFIalBkT1hpWEluQm5FUnFSU0JiNUtxSlV5VUk1LWk3eXMzYlJDZTVhY2hsVWRWdmVUY3N5TTNzOU9SQVdRNGcifQ",
      },
      vcId: "claimId:e237514acdf4a0a7",
      VCStored: true,
    },
  ]);

  const history = useHistory();
  useEffect(() => {
    console.log("GET_LORRY_RECEIPT_URL");
    console.log(user);

    if (user && user.isLogin) {
      if (false) {
        console.log("GET_LORRY_RECEIPT_URL ");

        axios
          .get(GET_LORRY_RECEIPT_URL + user.role, {
            headers: {Authorization: "Bearer " + user.accessToken},
          })
          .then(
            response => {
              if (response.status === 200) {
                if (
                  response.data.lorryReceipts !== undefined &&
                  response.data.lorryReceipts !== null
                )
                  setLRList(response.data.lorryReceipts);
              }
            },
            error => {
              console.log(error);
            }
          );
      }
    } else history.push("/");
  }, []);

  useEffect(() => console.log("LR List updated"), [lrList]);

  const updateStatus = (action, lr) => {
    axios
      .post(
        "https://9bppx3gm47.execute-api.ap-southeast-1.amazonaws.com/dev/lorryreceipt/action",
        {
          headers: {Authorization: "Bearer " + user.accessToken},
        },
        {
          lorryReceiptId: lr.lorryReceipt.id,
          action: action,
          role: user.role,
        }
      )
      .then(
        response => {
          if (response.status === 200) {
            axios
              .get(GET_LORRY_RECEIPT_URL + user.role, {
                headers: {Authorization: "Bearer " + user.accessToken},
              })
              .then(
                response => {
                  if (response.status === 200) {
                    if (
                      response.data.lorryReceipts !== undefined &&
                      response.data.lorryReceipts !== null
                    )
                      setLRList(response.data.lorryReceipts);
                  }
                },
                error => {
                  console.log(error);
                }
              );
          } else {
            alert(response.data.message);
          }
        },
        error => {
          console.log(error);
        }
      );
  };

  return (
    <div className="dashboard-container">
      <div className="heading dashboard-header">Lorry Receipt List</div>
      {user && user.role === "Transporter" ? (
        <Button
          className="dashboard-generate-lr"
          variant="light"
          onClick={() =>
            history.push({
              pathname: "/generateLR",
            })
          }
        >
          Generate LR
        </Button>
      ) : null}

      <div className="dashboard-table">
        <Table striped bordered hover variant="light">
          <thead>
            <tr>
              <th>LR No.</th>
              <th>VC ID</th>
              <th>Transporter</th>
              <th>Consigner</th>
              <th>Consignee</th>
              <th>Date</th>
              <th>Status</th>
              <th>Download PDF</th>
            </tr>
          </thead>
          <tbody>
            {lrList.map((lr, i) => (
              <tr key={i}>
                <td
                  className="dashboard-link"
                  onClick={() =>
                    history.push({
                      pathname: "/lr-details",
                      state: {
                        lrDetails: lr,
                      },
                    })
                  }
                >
                  {lr.lorryReceipt.receiptNumber}
                </td>
                <td>{lr.vcId.replace("claimId:", "")}</td>
                <td>{lr.transporter.fullName}</td>
                <td>{lr.consigner.fullName}</td>
                <td>{lr.consignee.fullName}</td>
                <td>{lr.lorryReceipt.createdAt}</td>
                <td>
                  <select
                    className="lrdetails-status"
                    onChange={e => updateStatus(e.target.value, lr)}
                    selected={lr.lorryReceipt.status}
                  >
                    <option value="Loaded">Loaded</option>
                    <option value="InTransit">InTransit</option>
                    <option value="Delivered">Delivered</option>
                  </select>
                </td>
                <td>
                  <DownloadPDF lrDetails={lr}></DownloadPDF>
                </td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </div>
  );
};

export default Dashboard;
