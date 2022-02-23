import React, { useEffect, useState } from "react";
import styled from "@emotion/styled";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useHistory } from "react-router-dom";
import CloseIcon from "@mui/icons-material/Close";
import CategoryTag from "../../atoms/CategoryTag/CategoryTag";
import {
	initialBtnType,
	selectCategory,
} from "../../../features/editFeatures/selectCategorySlice";
import { RootState } from "../../../app/store";
import { getAccessToken } from "../../../helpers/tokenControl";

const ModalMain = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	position: fixed;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #00000080;
	z-index: 10000;
`;

const ModalWrap = styled.div`
	width: 375px;
	height: 697px;
	background-color: #fff;
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
`;

const ModalInner = styled.div`
	margin: 20px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
`;

const BoldText = styled.p`
	font-size: 18px;
	font-weight: 600;
	margin-bottom: 10px;
	margin-top: 10px;
`;

const Text = styled.p`
	font-size: 15px;
	font-weight: 400;
	color: #999;
	padding-right: 10px;
`;

const Button = styled.div`
	width: 30%;
	height: 40px;
	background-color: #55b689;
	color: #fff;
	cursor: pointer;

	display: flex;
	justify-content: center;
	align-items: center;
`;

const Detail = styled.div`
	display: flex;
	align-items: center;
	margin-bottom: 5px;
	width: 100%;
`;

const ButtonWrap = styled.div`
	width: 100%;
	display: flex;
	justify-content: center;
	margin: 10px 0;
`;

const ModalHead = styled.div`
	width: 100%;
	height: 50px;
	background-color: #363636;
	color: #fff;
	font-weight: 500;
	display: flex;
	align-items: center;
	justify-content: space-between;
	& > p {
		margin-left: 20px;
	}

	& > svg {
		margin-right: 20px;
		cursor: pointer;
	}
`;

const InputStyle = styled.input`
	outline-style: none;
	border: 1px solid #999;
	width: auto;
`;

const RefuseWrap = styled.div`
	display: flex;
	align-items: center;
	justify-content: center;
	width: 100%;
	margin-top: 10px;
`;

const RefuseBtn = styled.p`
	color: #ff0000;
	font-size: 13px;
	font-weight: 600;
	cursor: pointer;
	margin-bottom: 10px;
`;

const InfoBox = styled.div`
	width: 100%;
	height: auto;
	display: flex;
	padding-bottom: 10px;
	flex-direction: column;
	align-items: flex-start;
`;

const CatagoryUl = styled.ul`
	width: 238px;
	margin: 10px 0 -10px 0;
	height: auto;
	display: flex;
	flex-wrap: wrap;
	li {
		letter-spacing: -0.009em;
		margin: 0 0.5rem 0.5rem 0;
		vertical-align: top;
		display: inline-block;
	}
`;

type modal = {
	modal: () => void;
	shopName: string;
	shopAddress: string;
	shopTel: string;
	shopCategory: Array<string>;
	shopOpen: string;
	shopClose: string;
	shopMatchId: string;
};

const ShopEditModal: React.FC<modal> = ({
	modal,
	shopName,
	shopAddress,
	shopTel,
	shopCategory,
	shopOpen,
	shopClose,
	shopMatchId,
}) => {
	const [changeName, setChangeName] = useState<string>(shopName);
	const [changeAddress, setChangeAddress] = useState<string>(shopAddress);
	const [changeTel, setChangeTel] = useState<string>(shopTel);
	const [changeCategory, setChangeCategory] =
		useState<Array<string>>(shopCategory);
	const [changeOpen, setChangeOpen] = useState<string>(shopOpen);
	const [changeClose, setChangeClose] = useState<string>(shopClose);

	const [shopInfo, setShopInfo] = useState({
		name: changeName,
		category: changeCategory,
		phone: changeTel,
		address: changeAddress,
		open: changeOpen,
		close: changeClose,
	});
	const formData = new FormData();
	const [image, setImage] = useState<File>();

	const shopNameChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeName(target.value);
		setShopInfo({
			...shopInfo,
			name: target.value,
		});
	};

	const shopAddressChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeAddress(target.value);
		setShopInfo({
			...shopInfo,
			address: target.value,
		});
	};

	const shopTelChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeTel(target.value);
		setShopInfo({
			...shopInfo,
			phone: target.value,
		});
	};

	const shopCategoryChange = (): void => {
		const newCategoryArr = categoryArr.filter((el) => {
			return el.isChecked === true;
		});
		const returnCategory = newCategoryArr.map((el) => {
			return el.categoryName;
		});
		setChangeCategory(returnCategory);
		setShopInfo({
			...shopInfo,
			category: returnCategory,
		});
		console.log(returnCategory);
		console.log(shopInfo);
	};

	const shopOpenChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeOpen(target.value);
		setShopInfo({
			...shopInfo,
			open: target.value,
		});
	};

	const shopCloseChange = (e: React.FormEvent<HTMLInputElement>): void => {
		const target = e.target as HTMLTextAreaElement;
		setChangeClose(target.value);
		setShopInfo({
			...shopInfo,
			close: target.value,
		});
	};

	const [categoryArr, setCategoryArr] = useState<initialBtnType[]>([]);
	const dispatch = useDispatch();
	const reduxStateCollector = useSelector((state: RootState) => {
		return state.selectCategory;
	});

	const checkCategory = () => {
		shopCategory.map((el) => {
			return dispatch(selectCategory(el));
		});
	};

	useEffect(() => {
		setCategoryArr(reduxStateCollector);
	}, [reduxStateCollector]);
	useEffect(() => {
		checkCategory();
	}, []);

	const onSaveFiles = (e: React.ChangeEvent<HTMLInputElement>) => {
		if (e.target.files) {
			const file: FileList = e.target.files;
			const uploadFile = file[0];
			setImage(uploadFile);
		}
	};

	const SHOP_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}`;
	const PostShopInfo = () => {
		const config = {
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
				"Content-Type": "multipart/form-data",
			},
		};
		return axios.post(SHOP_API_URL, formData, config);
	};
	const appendFormData = () => {
		const dtoObj = new Blob([JSON.stringify(shopInfo)], {
			type: "application/json",
		});
		formData.set("request", dtoObj);

		if (image) {
			formData.append("file", image);
		} else {
			const imageJson = new File([], "");
			formData.set("file", imageJson);
		}
	};
	const EditPost = () => {
		appendFormData();

		PostShopInfo().then(
			() => {
				console.log("post success");
				console.log(shopInfo);
			},
			(err) => {
				console.log("post fail");
				console.log(err);
			}
		);
	};

	const SHOP_DELETE_API_URL = `http://54.180.134.20/api/manager/shops/${shopMatchId}`;
	const DeleteShop = () => {
		return axios({
			method: "delete",
			url: `${SHOP_DELETE_API_URL}`,
			headers: {
				Authorization: `Bearer ${getAccessToken()}`,
			},
		});
	};
	const history = useHistory();
	const DeletePost = () => {
		// eslint-disable-next-line no-restricted-globals
		const res = confirm("정말로 삭제하시겠습니까?"); // eslint-disable-line no-alert
		if (res) {
			DeleteShop().then(
				() => {
					console.log("삭제완료");
					history.push("/profile");
					alert("삭제되었습니다."); // eslint-disable-line no-alert
				},
				() => {
					console.log("삭제실패");
				}
			);
		}
	};
	return (
		<ModalMain aria-hidden>
			<ModalWrap onClick={(e) => e.stopPropagation()} aria-hidden>
				<ModalHead>
					<p>가게 정보 수정</p>
					<CloseIcon onClick={modal} />
				</ModalHead>
				<ModalInner>
					<InfoBox>
						<BoldText>가게 이름 수정</BoldText>
						<Detail>
							<Text>가게 이름</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopNameChange}
								placeholder="가게 이름"
								defaultValue={changeName}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 상세 정보 수정</BoldText>
						<Detail>
							<Text>가게 주소</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopAddressChange}
								placeholder="가게 주소"
								defaultValue={changeAddress}
							/>
						</Detail>
						<Detail>
							<Text>가게 전화번호</Text>
							<InputStyle
								name="productName"
								type="text"
								onChange={shopTelChange}
								placeholder="가게 전화번호"
								defaultValue={changeTel}
							/>
						</Detail>
						<Detail>
							<Text>가게 카테고리</Text>
							<CatagoryUl>
								{categoryArr.map((card: initialBtnType) => {
									return (
										<li key={card.categoryName}>
											<CategoryTag
												text={card.categoryName}
												onClick={() => {
													dispatch(
														selectCategory(
															card.categoryName
														)
													);
													shopCategoryChange();
												}}
												isCheck={card.isChecked}
											/>
										</li>
									);
								})}
							</CatagoryUl>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 영업시간 수정</BoldText>
						<Detail>
							<Text>영업 시작</Text>
							<InputStyle
								name="productName"
								type="time"
								onChange={shopOpenChange}
								placeholder="상품 가격"
								value={changeOpen}
							/>
						</Detail>
						<Detail>
							<Text>영업 종료</Text>
							<InputStyle
								name="productName"
								type="time"
								onChange={shopCloseChange}
								placeholder="상품 할인 가격"
								value={changeClose}
							/>
						</Detail>
					</InfoBox>
					<InfoBox>
						<BoldText>가게 이미지 수정</BoldText>
						<Detail>
							<InputStyle
								name="productName"
								type="file"
								accept="image/*"
								placeholder="상품 이미지"
								onChange={onSaveFiles}
							/>
						</Detail>
					</InfoBox>
					<ButtonWrap>
						<Button onClick={EditPost}>수정</Button>
					</ButtonWrap>
					<RefuseWrap>
						<RefuseBtn onClick={DeletePost}>상점 삭제</RefuseBtn>
					</RefuseWrap>
				</ModalInner>
			</ModalWrap>
		</ModalMain>
	);
};

export default ShopEditModal;
