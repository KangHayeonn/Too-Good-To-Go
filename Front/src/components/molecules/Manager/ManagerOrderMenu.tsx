import styled from "@emotion/styled";
import React from "react";
import { useSelector } from "react-redux";
import { RootState } from "../../../app/store";
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
type managerMenuType = {
	shopId: string;
};

const ManagerOrderMenu: React.FC<managerMenuType> = ({ shopId }) => {
	const orderCountCount = useSelector((state: RootState) => {
		return state.managerOrderCount;
	});

	return (
		<HeaderMenu>
			<ManagerOrderBtnAtom
				to={`${shopId}/접수대기`}
				menuName="접수대기"
				orderAmount={orderCountCount.order_completed}
			/>
			<ManagerOrderBtnAtom
				to={`${shopId}/완료`}
				menuName="완료"
				orderAmount={orderCountCount.completed}
			/>
			<ManagerOrderBtnAtom
				to={`${shopId}/취소`}
				menuName="취소"
				orderAmount={orderCountCount.canceled}
			/>
			<ManagerOrderBtnAtom
				to={`${shopId}`}
				menuName="전체조회"
				orderAmount={orderCountCount.showAll}
			/>
		</HeaderMenu>
	);
};

export default ManagerOrderMenu;
