package com.ecoassitant.back.dto.result;

import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Responses of a project
 */
@NoArgsConstructor
public class GivenAnswersDto {
    private String projectId;
    private List<ResponseDto> responses;

    public Integer getProjectId() {
        return Integer.valueOf(projectId);
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId.toString();
    }

    public List<ResponseDto> getResponses() {
        return responses;
    }

    public void setResponses(List<ResponseDto> responses) {
        this.responses = List.copyOf(responses);
    }
}
