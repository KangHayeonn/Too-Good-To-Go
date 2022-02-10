import React from "react";
import { RouteComponentProps } from "react-router-dom";
import ManagerMenu from "../../components/molecules/Manager/ManagerMenu";
import ManagerMain from "../../components/organisms/Manager/ManagerMain";
import ManagerTemplate from "../../components/templates/ManagerTemplate";
import TitleTemplate from "../../components/templates/TitleTemplate";

type matchParams = {
	statusName: string;
};

const Manager: React.FC<RouteComponentProps<matchParams>> = ({ match }) => {
	return (
		<ManagerTemplate
			header={
				<TitleTemplate
					image="https://blog.kakaocdn.net/dn/bIrGtO/btqUWtBd0vE/Mj6Dly4xUgl0BK1a7MVJ51/img.jpg"
					title="교촌치킨 산본점"
					time="영업시간 10:00 ~ 22:00"
					address="산본본본"
					phone="02-1212-1234"
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
