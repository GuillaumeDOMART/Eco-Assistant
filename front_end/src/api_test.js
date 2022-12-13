export async function getTest() {

    try{
        const response = await fetch('/test');
        return await response.text();
    }catch(error) {
        return [error];
    }

}