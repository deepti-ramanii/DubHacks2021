import React, {Component} from "react";

interface UserIDProps {
    user_id: string;
    setUID(user_id: string): void;
}

class UserIDInput extends Component<UserIDProps> {
    onUIDChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        this.props.setUID(event.target.value);
    };

    render() {
        return (
            <div  id="user_id">
                <span> Enter an ID: </span>
                <input
                    value={this.props.user_id}
                    onChange={this.onUIDChange}
                />
            </div>
        )
    }
}

export default UserIDInput;