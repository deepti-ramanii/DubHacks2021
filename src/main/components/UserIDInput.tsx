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
                <input
                    value={this.props.user_id}
                    onChange={this.onUIDChange}
                />
        )
    }
}

export default UserIDInput;