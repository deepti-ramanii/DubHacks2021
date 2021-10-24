import React, {Component} from 'react'
import CreateAccountForm from "../CreateAccountForm";

interface UserMatchPollState {
    timer:NodeJS.Timeout;
}

interface UserMatchPollProps {
    CheckMatchStatus: () => Promise<void>;
}

class UserMatchPolling extends Component<UserMatchPollProps, UserMatchPollState> {
    constructor(props: UserMatchPollProps) {
        super(props);
        this.state = {
            timer: setInterval(
                () => this.props.CheckMatchStatus(),
                500
            )
        };
    }

    componentDidMount() {
        this.setState({
            timer: setInterval(
                () => this.props.CheckMatchStatus(),
                500
             )
        });
    }

    render() {
        return (
            <div>
            </div>
        );
    }
}
export default UserMatchPolling;