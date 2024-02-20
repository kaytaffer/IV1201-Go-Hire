package kth.iv1201.gohire.DTO;

import kth.iv1201.gohire.controller.util.ErrorType;

/**
 * Class representing an error response.
 */
public class ErrorDTO {

    private final ErrorType errorType;
    private final String specification;

    /**
     * Creates an instance of <code>ErrorDTO</code>
     * @param errorType Type of error
     * @param specification optional information about the error
     */
    public ErrorDTO(ErrorType errorType, String specification){
        this.errorType = errorType;
        this.specification = specification;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public String getSpecification() {
        return specification;
    }
}
