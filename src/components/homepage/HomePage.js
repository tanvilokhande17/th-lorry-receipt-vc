import React, {useContext, useEffect, useState} from "react";
import Login from "./Login";
import SignUp from "./SignUp";
import Info from "./Info";

function HomePage({}) {
  const [page, setPage] = useState("login");

  useEffect(() => {});

  return (
    <div style={{display: "flex", flexDirection: "row"}}>
      <Info />
      <div className="login">
        {page === "login" ? (
          <Login setPage={setPage} />
        ) : (
          <SignUp setPage={setPage} />
        )}
      </div>
    </div>
  );
}
export default HomePage;
