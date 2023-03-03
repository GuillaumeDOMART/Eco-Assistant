import Phase from "./Phase";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";

/**
 * FormConponnents
 * @param steps
 * @param activeStep activeStep
 * @param data data
 * @param selectedAnswers selectedAnswers
 * @param handleSubmit handleSubmit
 * @param handleChange handleChange
 * @param handleBack handleBack
 * @param register register
 * @param onSubmit onSubmit
 * @returns {JSX.Element} oui
 * @constructor
 */
function StepForm({
                      steps,
                      activeStep,
                      data,
                      selectedAnswers,
                      handleSubmit,
                      handleChange,
                      handleBack,
                      register,
                      onSubmit
                  }) {
    return (
        <form onSubmit={handleSubmit(onSubmit)}
              className="navbar-nav-scroll mt-4 col-8"
              style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}
        >
            {data.map(question => {
                    const check = selectedAnswers.find(answer => {
                        return question.dependance === answer.reponseId;
                    })
                    if (question.dependance === -1 || check) {
                        return (
                            <Phase key={question.questionId}
                                   register={register}
                                   value={question}
                                   onChange={handleChange}
                            />)
                    }
                    return (
                        <div key={question.questionId}>
                        </div>
                    );
                }
            )}
            <Box className="">
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

                    <Button type={"submit"}>
                        {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                    </Button>
                </Box>
            </Box>
        </form>
    );
}

export default StepForm