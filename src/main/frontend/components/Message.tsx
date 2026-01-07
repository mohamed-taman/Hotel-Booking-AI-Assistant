import Markdown from "react-markdown";

/**
 * Represents a single message exchanged in a conversation.
 *
 * @interface MessageItem
 * @property {'user' | 'assistant'} role - Indicates the source of the message, either from the user or the assistant.
 * @property {string} content - Contains the text content of the message.
 */
export interface MessageItem {
    role: 'user' | 'assistant';
    content: string;
}

interface MessageProps {
    message: MessageItem;
}

export default function Message({message}: MessageProps) {
    return (
        <div className="mb-l">
            <div className="font-bold">{message.role === 'user' ? '🧑‍💻 You' : '🤖 Assistant'}</div>
            <div>
                <Markdown>
                    {message.content}
                </Markdown>
            </div>
        </div>
    )
};