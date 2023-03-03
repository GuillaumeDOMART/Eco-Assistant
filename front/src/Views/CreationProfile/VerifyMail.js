import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Alert} from "react-bootstrap";


/**
 * Page for verify mail at the profil creation
 * @constructor the constructor
 */
function ProfilVerifyMail() {
    const navigate = useNavigate();
    const [showAlert, setShowAlert] = useState(false);


    useEffect(() => {
        const token = new URLSearchParams(window.location.search).get('token');

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`);

        const requestOptions = {
            method: 'PATCH',
            headers: myHeaders,
            redirect: 'follow'
        };
        fetch("/api/profil/register", requestOptions)
            .then(response => {
                if (response.status === 403) {
                    setShowAlert(true);
                } else {
                    sessionStorage.setItem("token", token)
                    navigate("/profil");
                }
            })
    }, [navigate]);

    return (
        <Alert show={showAlert} variant="danger">
            Lien d&apos;activation expiré
        </Alert>
        )
}

export default ProfilVerifyMail