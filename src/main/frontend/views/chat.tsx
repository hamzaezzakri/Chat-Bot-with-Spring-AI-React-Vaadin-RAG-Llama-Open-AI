export default function Chat(){

    async function send(){

    }


    return(
        <div className="p-m">
            <h3>Chat Bot</h3>
            <div>
                <input onChange={"target"}/>
                <button onClick={send}>Send</button>
            </div>
        </div>
    )
}