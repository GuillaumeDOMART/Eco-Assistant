import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


function Deconnection() {
    const navigate = useNavigate();

    useEffect(() => {
        sessionStorage.removeItem("token")
        navigate("/")
    }, [navigate]);

}

export default Deconnection