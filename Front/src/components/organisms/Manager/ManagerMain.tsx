import React from "react";
import styled from "@emotion/styled";
import ManagerOrderMenu from "../../molecules/Manager/ManagerOrderMenu";
import ManagerOrderLists from "./ManagerOrderLists";

const Main = styled.div`
	height: 600px;
	width: Calc(100% - 2px);
	background-color: #fff;
	border-bottom: 1px solid #333;
	border-left: 1px solid #333;
	border-right: 1px solid #333;
	display: flex;
`;

type matchParams = {
	statusMatchName: string;
	shopMatchId: string;
};

const ManagerMain: React.FC<matchParams> = ({
	statusMatchName,
	shopMatchId,
}) => {
	return (
		<Main>
			<ManagerOrderMenu />
			<ManagerOrderLists
				statusMatchName={statusMatchName}
				shopMatchId={shopMatchId}
			/>
		</Main>
	);
};

export default ManagerMain;
