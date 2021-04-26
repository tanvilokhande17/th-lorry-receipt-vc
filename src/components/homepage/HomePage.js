import React, {useState} from "react";
import Login from "./Login";
import SignUp from "./SignUp";
import Info from "./Info";

function HomePage({}) {
  const [page, setPage] = useState("login");

  return (

    <><Info/>
    <div className="login">
      {page === "login" ? (
        <Login setPage={setPage} />
      ) : (
        <SignUp setPage={setPage} />
      )}
    </div>
    </>
  );
}
export default HomePage;
