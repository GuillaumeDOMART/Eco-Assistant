import BarreNavCore from "../BarreNav/BarreNavCore";
import React from "react";

function Profil(){
    return(
        <div id="app" className="container-fluid row w-100 h-100 m-0 p-0">
            <BarreNavCore/>
            <div className="col-10 p-5">
                <InfoProfil/>
            </div>
        </div>
    );
}
function InfoProfil(){

}
export default Profil;