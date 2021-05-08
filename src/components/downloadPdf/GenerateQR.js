import axios from "axios";
import React, { useState, useEffect, useRef } from "react";
import { Button, Modal } from "react-bootstrap";
import { createGlobalStyle } from "styled-components";
import { VERIFICATION_URL } from "../util/Constant";
import { getUser } from "../util/User";
import {createPdfFromHtml} from "./GeneratePDF";




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



const GenerateQR = ({ lorryReceiptId, role }) => {


  const user = getUser();
  const [qrUrl, setQrUrl] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const download = () => {
    console.log("Generate QR ");
    console.log("vcId--->" + lorryReceiptId);
    console.log("status--->" + role);
    console.log("VERIFICATION_URL--->" + VERIFICATION_URL);
    console.log("user.accessToken--->" + user.accessToken);

    if (lorryReceiptId && role) {
      const param = {
        lorryReceiptId: lorryReceiptId,
        role: role,
      };
      axios
        .post(VERIFICATION_URL, param, {
          headers: {
            Authorization: "Bearer " + user.accessToken
          },
        })
        .then(
          response => {
            if (response.status === 200) {
              const res = response.data.qrCode;
              console.log("res----->" + res);
              setQrUrl(res);
              setShowModal(true);
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

      <Button
        className="dashboard-link"
        variant="light"
        onClick={() => download()}
      >
        verification
        </Button>
      {showModal === true ? (

        <QRCodeData
          showModal={showModal}
          setShowModal={setShowModal}
          qrUrl= {qrUrl}
          setQrUrl = {setQrUrl}
        />
      ) : null}
    </>
  );
};

export default GenerateQR;

export const QRCodeData = ({ showModal, setShowModal, qrUrl, setQrUrl }) => {
  

  const [printContent, setPrintContent] = useState(1);
  const handleClose = () => setShowModal(false);
 
  return (

    <>
      <Modal className="QrCodeModal" show={showModal} onHide={handleClose} animation={false}>
        <div class="modal-content">
        <Modal.Header closeButton>
          <Modal.Title>QR Code Image</Modal.Title>
        </Modal.Header>
        <Modal.Body>
            <div ref={el => setPrintContent(el)}>
            <center>
              <img src={qrUrl} />
            </center>
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
        </div>
      </Modal>
    </>
  );
};
