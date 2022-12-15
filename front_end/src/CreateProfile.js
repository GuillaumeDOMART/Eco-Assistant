import {Button, Col, Container, InputGroup, Row} from "react-bootstrap";
import {Form} from "react-bootstrap";
import './CreateProfile.css'
import {useState} from "react";

function CreateProfile() {

    const [infos, setInfos] = useState(
        [
            {element : "name", value : ""},
            {element : "firstName", value : ""},
            {element : "mail", value : ""},
            {element : "mdp", value : ""},
            {element : "confMdp", value : ""}
        ]
    )

    const [nameOnChange, setName]= useState("")
    const [firstNameOnChange, setFirstName]= useState("")
    const [mailOnChange, setMail]= useState("")
    const [mdpOnChange, setMdp]= useState("")
    const [confMdpOnChange, setConfMdp]= useState("")

    const [response, setResponse] = useState("")
    const handleClick = () => {
        const infosCopy = []
        infosCopy.push({element : "name", value : nameOnChange})
        infosCopy.push({element : "name", value : firstNameOnChange})
        infosCopy.push({element : "name", value : mailOnChange})
        infosCopy.push({element : "name", value : mdpOnChange})
        infosCopy.push({element : "name", value : confMdpOnChange})

        setInfos(infosCopy)

        // fetch('/test')
        //     .then(response => console.log(response.status));
        // console.log("fetch")
        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                    "email": mailOnChange,
                    "password": mdpOnChange,
                    "nom" : nameOnChange,
                    "prenom" : firstNameOnChange
            })
        };
        fetch('/profile/add', requestOptions)
            .then(response => console.log(response.status))
    }

    const handleGet = () => {
        const infosCopy = []
        infosCopy.push({element : "name", value : mailOnChange})
        infosCopy.push({element : "name", value : mdpOnChange})

        setInfos(infosCopy)

        const requestOptions = {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(
                {
                    "email": mailOnChange,
                    "password": mdpOnChange,
                    "nom": "",
                    "prenom": ""
                })
        };
        fetch('/profile/get', requestOptions)
            .then(response => console.log(response.json()))
    }

    const handleChangeName = (event) => {
        setName(event.target.value)
    }
    const handleChangeFirstName = (event) => {
        setFirstName(event.target.value)
    }
    const handleChangeMail = (event) => {
        setMail(event.target.value)
    }
    const handleChangeMdp = (event) => {
        setMdp(event.target.value)
    }
    const handleChangeConfMdp = (event) => {
        setConfMdp(event.target.value)
    }

    return (
        <div>
            <Container className="text-center">
                <Row>
                    <Col>
                        <h1>Créer un profil</h1>
                        <Form className="form-example">
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Nom"
                                    aria-label="Nom"
                                    aria-describedby="basic-addon1"
                                    onChange={handleChangeName}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Prenom"
                                    aria-label="Prenom"
                                    aria-describedby="basic-addon1"
                                    onChange={handleChangeFirstName}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Adresse E-mail"
                                    aria-label="Mail"
                                    aria-describedby="basic-addon1"
                                    defaultValue={""}
                                    onChange={handleChangeMail}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Mot De Passe"
                                    aria-label="Mdp"
                                    aria-describedby="basic-addon1"
                                    type="password"
                                    onChange={handleChangeMdp}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Confirmer le mot de passe"
                                    aria-label="ConfMdp"
                                    aria-describedby="basic-addon1"
                                    type="password"
                                    onChange={handleChangeConfMdp}
                                />
                            </InputGroup>
                            <Button id="button" onClick={handleClick}>Créer un Profil</Button>
                        </Form>
                    </Col>
                    <Col>
                        <h1>Se connecter</h1>
                        <form action="" method="get" className="form-example">
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Identifiant (adresse e-mail)"
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    onChange={handleChangeMail}
                                />
                            </InputGroup>
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Mot de passe"
                                    aria-label="Username"
                                    aria-describedby="basic-addon1"
                                    onChange={handleChangeMdp}
                                />
                            </InputGroup>
                            <Button id="button" onClick={handleGet}>Se connecter</Button>
                        </form>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default CreateProfile