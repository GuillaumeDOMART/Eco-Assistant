import {useCallback, useState} from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Questionnaire from "../../Views/Quiz/Questionnaire";
import {useNavigate} from "react-router-dom";

const steps = ["Conception", "Developpement", "Test", "Production", "Maintenance"];

/**
 * The component representing the Stepper
 * @returns {JSX.Element}
 * @constructor
 */
export default function StepperComponent() {
    const [activeStep, setActiveStep] = useState(0);
    const [skipped, setSkipped] = useState(new Set());
    const navigate = useNavigate()

    /**
     * Check if the step is in the skipped Set
     * @param step
     * @returns {boolean}
     */
    const isStepSkipped = useCallback(
        (step) => {
            return skipped.has(step);
        },
        [skipped]
    );

    /**
     * Go to the next step
     */
    const handleNext = useCallback(
        () => {
            let newSkipped = skipped;
            if (isStepSkipped(activeStep)) {
                newSkipped = new Set(newSkipped.values());
                newSkipped.delete(activeStep);
            }

            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            setSkipped(newSkipped);
        },
        [skipped, activeStep, isStepSkipped]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
        },
        []
    );

    /**
     * Skip the actual step
     */
    const handleSkip = useCallback(
        () => {

            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            setSkipped((prevSkipped) => {
                const newSkipped = new Set(prevSkipped.values());
                newSkipped.add(activeStep);
                return newSkipped;
            });
        },
        [activeStep]
    );

    /**
     * Reset the stepper
     */
    const handleReset = useCallback(
        () => {
            const projectId = sessionStorage.getItem("project")
            sessionStorage.removeItem("project")
            navigate(`/result?id=${projectId}`)
        },
        [navigate]
    );

    return (
        <div>
            <Box sx={{width: '100%', pl: '20px', pr: '20px', mt: '20px'}}>
                <Stepper activeStep={activeStep} alternativeLabel>
                    {steps.map((label, index) => {
                        const stepProps = {};
                        const labelProps = {};
                        if (isStepSkipped(index)) {
                            stepProps.completed = false;
                        }
                        return (
                            <Step key={label} {...stepProps}>
                                <StepLabel {...labelProps}>{label}</StepLabel>
                            </Step>
                        );
                    })}
                </Stepper>
            </Box>
            <Box sx={{width: '100%', position: 'fixed', bottom: '0', mb: '20px', pl: '20px', pr: '20px'}}>
                {activeStep === steps.length ? (
                    <>
                        <Typography sx={{mt: 2, mb: 1}}>
                            All steps completed - you&apos;re finished
                        </Typography>
                        <Box sx={{display: 'flex', flexDirection: 'row', pt: 2}}>
                            <Box sx={{flex: '1 1 auto'}}/>
                            <Button onClick={handleReset}>Reset</Button>
                        </Box>
                    </>
                ) : (
                    <>
                        <Typography sx={{mt: 2, mb: 1}}>Step {activeStep + 1}</Typography>
                        <Box sx={{display: 'flex', flexDirection: 'row', pt: 2}}>
                            <Button
                                color="inherit"
                                disabled={activeStep === 0}
                                onClick={handleBack}
                                sx={{mr: 1}}
                            >
                                Back
                            </Button>
                            <Box sx={{flex: '1 1 auto'}}/>
                            <Button color="success" onClick={handleSkip} sx={{mr: 1}}>
                                Skip
                            </Button>

                            <Button onClick={handleNext}>
                                {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                            </Button>
                        </Box>
                    </>
                )}
            </Box>
            <Questionnaire activeStep={activeStep}/>
        </div>

    );
}