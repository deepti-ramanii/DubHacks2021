import React, {Component} from "react";
import UserIDInput from "./Components/UserIDInput";
import AgeInput from "./Components/AgeInput";
import PlaystyleInput from "./Components/PlaystyleInput";
import AgePrefInput from "./Components/AgePrefInput";
import PlaystylePrefInput from "./Components/PlaystylePrefInput";
import HobbyInput from "./Components/HobbyInput";
import CreateAccountForm from "./CreateAccountForm";
import UpdatePreferencesMatchingForm from "./UpdatePreferencesMatchingForm";
import {log} from "util";

interface GameState {

}

interface GameProps {

}

/**
 * CreateAccountForm allows user to input string.
 */
class Game extends Component<GameProps, GameState> {
    constructor(props: GameProps) {
        super(props);
        this.state = {

        };
    }

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div>
                <span> This is our game! </span>
            </div>
        );
    }
}
export default Game;
