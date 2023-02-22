import {useEffect} from "react";
import {useNavigate} from "react-router-dom";


/**
 * Page for the logout
 * @constructor the constructor
 */
function Deconnection() {
    const navigate = useNavigate();

    useEffect(() => {
        sessionStorage.removeItem("token")
        navigate("/")
    }, [navigate]);

}

export default Deconnection