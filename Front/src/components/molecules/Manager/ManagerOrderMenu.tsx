import styled from "@emotion/styled";
import React from "react";
import ManagerOrderBtnAtom from "../../atoms/MenuButton/ManagerOrderBtnAtom";

const HeaderMenu = styled.ul`
	display: flex;
	flex-direction: column;
	width: 80px;
	height: 100%;

	& > hr {
		width: auto;
	}
`;

const ManagerOrderMenu: React.FC = () => {
	return (
		<HeaderMenu>
			<ManagerOrderBtnAtom
				to="접수대기"
				menuName="접수대기"
				orderAmount={0}
			/>
			<ManagerOrderBtnAtom
				to="처리중"
				menuName="처리중"
				orderAmount={0}
			/>
			<ManagerOrderBtnAtom to="완료" menuName="완료" orderAmount={0} />
			<ManagerOrderBtnAtom to="" menuName="전체조회" orderAmount={0} />
		</HeaderMenu>
	);
};

export default ManagerOrderMenu;
