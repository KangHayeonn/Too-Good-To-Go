import React from "react";
import { RouteComponentProps } from "react-router-dom";
import ManagerMenu from "../../components/molecules/Manager/ManagerMenu";
import ManagerMain from "../../components/organisms/Manager/ManagerMain";
import ManagerTemplate from "../../components/templates/ManagerTemplate";

type matchParams = {
	statusName: string;
};

const Manager: React.FC<RouteComponentProps<matchParams>> = ({ match }) => {
	return (
		<ManagerTemplate header={<ManagerMenu />}>
			<ManagerMain
				statusMatchName={match.params.statusName || "전체조회"}
			/>
		</ManagerTemplate>
	);
};

export default Manager;
