import styled from "@emotion/styled";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../../../app/store";
import { countOrder } from "../../../features/manager/ManagerOrderCountSlice";
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
	const dispatch = useDispatch();
	const orderCountCount = useSelector((state: RootState) => {
		return state.managerOrderCount;
	});

	useEffect(() => {
		dispatch(countOrder());
	}, []);

	return (
		<HeaderMenu>
			<ManagerOrderBtnAtom
				to="접수대기"
				menuName="접수대기"
				orderAmount={orderCountCount[0].order_completed}
			/>
			<ManagerOrderBtnAtom
				to="처리중"
				menuName="처리중"
				orderAmount={orderCountCount[0].preparing}
			/>
			<ManagerOrderBtnAtom
				to="완료"
				menuName="완료"
				orderAmount={orderCountCount[0].completed}
			/>
			<ManagerOrderBtnAtom
				to=""
				menuName="전체조회"
				orderAmount={orderCountCount[0].showAll}
			/>
		</HeaderMenu>
	);
};

export default ManagerOrderMenu;
