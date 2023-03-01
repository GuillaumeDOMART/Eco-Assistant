import StepLabel from "@mui/material/StepLabel";
import Step from "@mui/material/Step";
import Stepper from "@mui/material/Stepper";
import Box from "@mui/material/Box";

/**
 * components
 * @param activeStep activeStep
 * @returns {JSX.Element} truc
 * @constructor
 */
function StepBox({activeStep, steps}) {
    return (
        <Box className="mt-3">
            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label) => {
                    const stepProps = {};
                    const labelProps = {};
                    return (
                        <Step key={label} {...stepProps}>
                            <StepLabel {...labelProps}>{label}</StepLabel>
                        </Step>
                    );
                })}
            </Stepper>
        </Box>
    );
}

export default StepBox