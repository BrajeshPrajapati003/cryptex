export class ApiError extends Error {
    readonly status: number; 
    // readOnly because later we don't want them to be changed once the error is constructed
    readonly data: unknown;

    constructor(
        message: string, 
        status: number,
        data?: unknown,
    ){
        super(message);

        this.name = "ApiError";
        this.status = status;
        this.data = data;
    }
}
