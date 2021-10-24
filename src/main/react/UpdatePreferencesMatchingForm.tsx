import React, {Component} from "react";
import AgePrefInput from "./Components/AgePrefInput";
import PlaystylePrefInput from "./Components/PlaystylePrefInput";
import HobbyPrefInput from "./Components/HobbyPrefInput";

interface UpdatePrefsMatchingFormProps {
    user_id:string;
}

interface UpdatePrefsMatchingFormState {
    min_age_pref:number;
    max_age_pref:number;
    competitive_pref:boolean;
    uses_vc_pref:boolean;
    use_hobbies:boolean;
}

/**
 * CreateAccountForm allows user to input string.
 */
class UpdatePreferencesMatchingForm extends Component<UpdatePrefsMatchingFormProps, UpdatePrefsMatchingFormState> {
    constructor(props: UpdatePrefsMatchingFormProps) {
        super(props);
        this.state = {
            min_age_pref: 0,
            max_age_pref: 255,
            competitive_pref: false,
            uses_vc_pref: false,
            use_hobbies: false
        };
    }

    setMinAgePref = (min_age: number) => {
        this.setState({
            min_age_pref: min_age
        });
    };

    setMaxAgePref = (max_age: number) => {
        this.setState({
            max_age_pref: max_age
        });
    };

    setCompetitivePref = () => {
        const old_competitive = this.state.competitive_pref;
        this.setState({
            competitive_pref: !old_competitive
        });
    };

    setUsesVCPref = () => {
        const old_usesVC = this.state.uses_vc_pref;
        this.setState({
            uses_vc_pref: !old_usesVC
        });
    };

    setUseHobbies = () => {
        const old_useHobbies = this.state.use_hobbies;
        this.setState({
            use_hobbies: !old_useHobbies
        });
    };

    UpdatePrefs = async () => {
        let response = await fetch('http://localhost:4567/update-preferences?user_id=' + this.props.user_id +
                                                                                 '&min_age=' + this.state.min_age_pref +
                                                                                 '&max_age=' + this.state.max_age_pref +
                                                                             '&competitive=' + this.state.competitive_pref +
                                                                                 '&uses_vc=' + this.state.uses_vc_pref +
                                                                             '&use_hobbies=' +  this.state.use_hobbies);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
        let responsePath = await response.json();
        const successful = JSON.stringify(responsePath);
        if (successful != 'true') {
            alert('Invalid username. Please try again.');
        }
    };

    FindMatch = async () => {
        let response = await fetch('http://localhost:4567/update-preferences?user_id=' + this.props.user_id);
        if (!response.ok) {
            alert("The status is wrong! Expected: 200, Was: " + response.status);
            return;
        }
        let responsePath = await response.json();
        const playerMatch = JSON.stringify(responsePath);
        if (playerMatch === 'true') {
            alert('Found ' + playerMatch);
        } else {
            alert('No matches found.');
        }
    };

    // RENDERING METHODS ---------------------------------------------------------------------------
    render() {
        return (
            <div>
                <div id="user_prefs">
                    <span>Preferences for {this.props.user_id}: </span>
                </div>
                <div id="age_info">
                    <AgePrefInput min_age={this.state.min_age_pref} max_age={this.state.max_age_pref} setMinAge={this.setMinAgePref} setMaxAge={this.setMaxAgePref}/>
                </div>
                <div id="user_prefs">
                    <span>Check the boxes which apply to people you want to play with: </span>
                </div>
                <div id="playstyle_prefs">
                    <PlaystylePrefInput toggleCompetitivePref={this.setCompetitivePref} toggleUsesVCPref={this.setUsesVCPref}/>
                </div>
                <div id="hobby_prefs">
                    <HobbyPrefInput toggleUseHobbies={this.setUseHobbies}/>
                </div>
                <div id="update">
                    <button onClick={this.UpdatePrefs}>Update</button>
                </div>
                <div id="match">
                    <button onClick={this.FindMatch}>Match</button>
                </div>
            </div>
        );
    }
}
export default UpdatePreferencesMatchingForm;
