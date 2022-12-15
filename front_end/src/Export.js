import {Button, Col, Container, Form, InputGroup, Row} from "react-bootstrap";

function Export() {

    return (
        <div>
            <Container className="text-center">
                <Row>
                    <h1>Interaction Extérieure</h1>
                </Row>
                <Row>
                    <Col>
                        <Button variant="primary">Exporter au format PDF</Button>
                    </Col>
                    <Col>
                        <Form className="form-example">
                            <InputGroup className="mb-3">
                                <Form.Control
                                    placeholder="Nom"
                                    aria-label="Nom"
                                    aria-describedby="basic-addon1"
                                />
                            </InputGroup>
                        </Form>
                        <Button variant="primary">Envoyer un mail</Button>
                    </Col>
                </Row>
            </Container>
        </div>
    )
}

export default Export