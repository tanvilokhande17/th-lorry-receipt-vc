import axios from "axios";
import React, {useState, useEffect, useRef} from "react";
import {Button, Modal} from "react-bootstrap";
import {createGlobalStyle} from "styled-components";
import {API_KEY, WALLET_CREDENTIALS_URL} from "../util/Constant";
import {getUser} from "../util/User";
import {createPdfFromHtml} from "./GeneratePDF";
import LorryReceiptPDF from "./LorryReceiptPDF";

const Global = createGlobalStyle`
  /* paper.css */
  https://github.com/cognitom/paper-css

  /* @page { margin: 0 } */
  #print {
    margin: 0;
    font-family: "IPAexGothic", sans-serif;
  }
  .sheet {
    margin: 0;
    overflow: hidden;
    position: relative;
    box-sizing: border-box;
    page-break-after: always;
  }
  
  
  /** Paper sizes **/
  #print.A3               .sheet { width: 297mm; height: 419mm }
  #print.A3.landscape     .sheet { width: 420mm; height: 296mm }
  #print.A4               .sheet { width: 210mm; height: 296mm }
  #print.A4.landscape     .sheet { width: 297mm; height: 209mm }
  #print.A5               .sheet { width: 148mm; height: 209mm }
  #print.A5.landscape     .sheet { width: 210mm; height: 147mm }
  #print.letter           .sheet { width: 216mm; height: 279mm }
  #print.letter.landscape .sheet { width: 280mm; height: 215mm }
  #print.legal            .sheet { width: 216mm; height: 356mm }
  #print.legal.landscape  .sheet { width: 357mm; height: 215mm }
  
  /** Padding area **/
  .sheet.padding-10mm { padding: 10mm }
  .sheet.padding-15mm { padding: 15mm }
  .sheet.padding-20mm { padding: 20mm }
  .sheet.padding-25mm { padding: 25mm }
  
  /** For screen preview **/
  @media screen {
    body {
      background: #E0E0E0;
      height: 100%;
    }
    .sheet {
      background: #FFFFFF;
      /* margin: 5mm auto; */
      padding: 5mm 0;
    }
  }
  
  /** Fix for Chrome issue #273306 **/
  @media print {
    #print.A3.landscape            { width: 420mm }
    #print.A3, #print.A4.landscape { width: 297mm }
    #print.A4, #print.A5.landscape { width: 210mm }
    #print.A5                      { width: 148mm }
    #print.letter, #print.legal    { width: 216mm }
    #print.letter.landscape        { width: 280mm }
    #print.legal.landscape         { width: 357mm }
  }
`;

const DownloadPDF = ({vcId, status}) => {
  const user = getUser();
  const [lrDetails, setLrDetails] = useState({
    consignee: {
      name: "Mandar Tawde",
      address: "Mumbai",
      mobileNumber: "7303399868",
    },
    consigner: {
      name: "Mandar Tawde",
      address: "Mumbai",
      mobileNumber: "7303399867",
    },
    consignments: [
      {
        quantity: "3",
        weight: "100 KG",
        description: "Raw agricultural material",
      },
      {quantity: "10", weight: "200 KG", description: "Textile goods"},
    ],
    date: "Tue Apr 27 08:17:28 UTC 2021",
    driver: {
      name: "Mandar Tawde",
      address: "Mumbai",
      mobileNumber: "7303399866",
    },
    freightCharge: {freight: "10000 Rs", advance: "5000 Rs", toPay: "5000 Rs"},
    loadingAddress: "LUDHIYANA",
    receiptNumber: "LRN0000011",
    totalWeight: "300 KG",
    transporter: {
      name: "Tanvi",
      address: "Address",
      mobileNumber: "9158447698",
    },
    unloadingAddress: "MUMBAI",
    vcId: "claimId:e237514acdf4a0a7",
    status: "BOOKED",
    vehicleNumber: "MH47B1936",
  });
  const [showModal, setShowModal] = useState(false);
  const download = () => {
    console.log("Download PDF");
    setShowModal(true);
    if (vcId) {
      axios
        .get(WALLET_CREDENTIALS_URL + vcId, {
          headers: {
            Authorization: "Bearer " + user.accessToken,
            "Api-Key": API_KEY,
          },
        })
        .then(
          response => {
            if (response.status === 200) {
              const res = response.data.credentialSubject.data;

              setLrDetails({...res, vcId: vcId, status: status});
            }
          },
          error => {
            console.log(error);
          }
        );
    }
  };

  return (
    <>
      <div className="dashboard-link" onClick={() => download()}>
        Download PDF
      </div>
      {showModal === true && lrDetails !== null ? (
        <CreatePDF
          lrDetails={lrDetails}
          showModal={showModal}
          setShowModal={setShowModal}
        />
      ) : null}
    </>
  );
};

export default DownloadPDF;

export const CreatePDF = ({lrDetails, showModal, setShowModal}) => {
  const [printContent, setPrintContent] = useState(1);
  const handleClose = () => setShowModal(false);
  return (
    <>
      <Modal show={showModal} onHide={handleClose} animation={false}>
        <Modal.Header closeButton>
          <Modal.Title>Lorry Receipt</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Global />
          <LorryReceiptPDF lrDetails={lrDetails} />
          <div id="print" className="A4">
            <div style={{position: "fixed", top: "200vh"}}>
              <div ref={el => setPrintContent(el)}>
                <LorryReceiptPDF lrDetails={lrDetails} />
              </div>
            </div>
          </div>
        </Modal.Body>

        <Modal.Footer>
          <Button variant="secondary" onClick={handleClose}>
            Close
          </Button>
          <Button
            variant="primary"
            onClick={() => {
              createPdfFromHtml(printContent);
              setShowModal(false);
            }}
          >
            Download
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  );
};
