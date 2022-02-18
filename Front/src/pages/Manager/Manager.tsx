import React from "react";
import { RouteComponentProps } from "react-router-dom";
import ManagerMenu from "../../components/molecules/Manager/ManagerMenu";
import ManagerMain from "../../components/organisms/Manager/ManagerMain";
import ManagerTemplate from "../../components/templates/ManagerTemplate";
import ShopTitles from "../../components/organisms/Shop/ShopTitles";

type matchParams = {
	statusName: string;
};

const Manager: React.FC<RouteComponentProps<matchParams>> = ({ match }) => {
	return (
		<ManagerTemplate
			header={
				<ShopTitles
					shopMatchId={match.params.statusName}
					isEdit={false}
				/>
			}
		>
			<ManagerMenu />
			<ManagerMain
				statusMatchName={match.params.statusName || "전체조회"}
			/>
		</ManagerTemplate>
	);
};

export default Manager;
