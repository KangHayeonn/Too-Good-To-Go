import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import ManagerOrderList from "../../molecules/Manager/ManagerOrderList";
import { getAccessToken } from "../../../helpers/tokenControl";
import { RootState } from "../../../app/store";
import { countOrder } from "../../../features/manager/ManagerOrderCountSlice";

const Ul = styled.ul`
	width: 100%;
	display: flex;
	flex-direction: column;
	align-items: center;
	overflow: auto;
`;

const checkStatus = (status: string) => {
	switch (status) {
		case "WAITING_FOR_ACCEPTANCE":
			return "접수대기";
		case "CANCELED":
			return "취소";
		case "ACCEPTED":
			return "완료";
		default:
			return "오류";
	}
};

const checkStatus2 = (status: string) => {
	switch (status) {
		case "WAITING_FOR_ACCEPTANCE":
			return "접수대기";
		case "CANCELED":
			return "취소";
		case "ACCEPTED":
			return "완료";
		default:
			return "오류";
	}
};

interface statusMatchType {
	statusMatchName: string;
	shopMatchId: string;
}

type orderType = {
	id: number;
	user: {
		id: number;
		phone: string;
	};
	products: [
		{
			id: number;
			quantity: number;
			name: string;
			price: number;
			discountedPrice: number;
		}
	];
	status: string;
	requirement: string;
	paymentMethod: string;
	needDisposables: boolean;
	createdAt: string;
	eta: string;
};

const ManagerOrderLists: React.FC<statusMatchType> = ({
	statusMatchName,
	shopMatchId,
}) => {
	const [items, setItems] = useState([]);
	const [orderList, setOrderList] = useState<orderType[]>([]);
	const dispatch = useDispatch();
	const orderCountCount = useSelector((state: RootState) => {
		return state.managerOrderCount;
	});
	const orderDivide = () => {
		const orderCompletedCount = orderList.filter(
			(r) => r.status === "WAITING_FOR_ACCEPTANCE"
		).length;
		const prepareCount = orderList.filter(
			(r) => r.status === "ACCEPTED"
		).length;
		const cancelCount = orderList.filter(
			(r) => r.status === "CANCELED"
		).length;
		const allCount = orderList.length;
		dispatch(
			countOrder({
				order_completed: orderCompletedCount,
				completed: prepareCount,
				canceled: cancelCount,
				showAll: allCount,
			})
		);
		console.log(orderCountCount);
	};

	const MANAGER_ORDER_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}/orders`;
	const BoardService = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		};
		return axios.get(MANAGER_ORDER_API_URL, config);
	};
	useEffect(() => {
		BoardService().then(
			(res) => {
				setOrderList(res.data.data); // api가 연결 된 경우 -> back에서 데이터 불러옴
				console.log(res.data.data);
				if (orderList.length >= 1) {
					orderDivide();
				}
				console.log(statusMatchName);
			},
			() => {
				console.log("error"); // api가 연결되지 않은 경우 -> 위의 예시 데이터 출력
			}
		);
	}, [statusMatchName]);
	return (
		<Ul>
			{orderList.map((row) =>
				statusMatchName === checkStatus2(row.status) ||
				statusMatchName === "전체조회" ? (
					<ManagerOrderList
						key={row.id}
						orderId={row.id}
						orderTime={row.createdAt}
						// orderTime={moment(row.createdAt).format(
						// 	"YYYY년 MM월 DD일 hh시 mm분 ss초"
						// )}
						orderList={row.products.map(
							(r) => `${r.name} ${r.quantity.toString()}개 / `
						)}
						orderPrice={
							row.products
								.map((r) => r.discountedPrice * r.quantity)
								.reduce((a, b) => a + b)
								.toString()
								.replace(/\B(?=(\d{3})+(?!\d))/g, ",") // 가격 1000단위 콤마
						}
						shopTell={row.user.phone}
						orderDetail={row.requirement}
						payment={row.paymentMethod}
						needDisposables={row.needDisposables}
						status={checkStatus(row.status)}
						eta={row.eta}
					/>
				) : null
			)}
		</Ul>
	);
};

export default ManagerOrderLists;
