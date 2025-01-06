import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  User,
  Mail,
  Edit,
  CheckCircle,
  DollarSign,
  Calendar,
} from "lucide-react";
import Navbar from "./Navbar";

const ProfilePage = () => {
  const [isEditing, setIsEditing] = useState(false);
  const [userProfile, setUserProfile] = useState(null);
  const [editedProfile, setEditedProfile] = useState(null);
  const [donationHistory, setDonationHistory] = useState([]);

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        if (!user || !user.id) {
          alert("User belum login.");
          return;
        }

        const response = await axios.get(
          `http://localhost:8080/api/user/profile/${user.id}`
        );

        setUserProfile(response.data.user);
        setEditedProfile(response.data.user);
        setDonationHistory(response.data.donations);
      } catch (error) {
        console.error("Error fetching user profile:", error);
        alert("Gagal memuat profil. Silakan coba lagi.");
      }
    };

    fetchUserProfile();
  }, []);

  const handleEditToggle = async () => {
    if (isEditing) {
      try {
        const user = JSON.parse(localStorage.getItem("user"));
        const response = await axios.put(
          `http://localhost:8080/api/user/${user.id}`,
          {
            username: editedProfile.username,
            email: editedProfile.email,
          }
        );
        setUserProfile(response.data);
        alert("Profil berhasil diperbarui.");
      } catch (error) {
        console.error("Error updating profile:", error);
        alert("Gagal memperbarui profil. Silakan coba lagi.");
      }
    }
    setIsEditing(!isEditing);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setEditedProfile((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const formatCurrency = (value) => {
    return `Rp ${value.toLocaleString("id-ID")}`;
  };

  if (!userProfile) {
    return <p>Loading...</p>;
  }

  const isAdmin = userProfile.role === "admin";

  return (
    <div className="min-h-screen bg-gray-50 pt-20">
      <Navbar isAdmin={isAdmin} />
      <div className="container mx-auto max-w-4xl px-4 py-8">
        <div className="bg-white shadow-lg rounded-lg p-8">
          <div className="flex justify-between items-center mb-8">
            <h1 className="text-3xl font-bold text-blue-600 flex items-center">
              <User className="mr-3 text-blue-500" /> Profil Saya
            </h1>
            <button
              onClick={handleEditToggle}
              className="flex items-center text-blue-600 hover:text-blue-800"
            >
              {isEditing ? (
                <>
                  <CheckCircle className="mr-2 h-5 w-5" /> Simpan
                </>
              ) : (
                <>
                  <Edit className="mr-2 h-5 w-5" /> Edit
                </>
              )}
            </button>
          </div>

          <div className="grid md:grid-cols-2 gap-6">
            <div>
              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Nama Lengkap
                </label>
                {isEditing ? (
                  <input
                    type="text"
                    name="username"
                    value={editedProfile.username}
                    onChange={handleInputChange}
                    className="w-full p-2 border rounded-md"
                  />
                ) : (
                  <div className="flex items-center">
                    <User className="mr-2 text-gray-500" />
                    <span>{userProfile.username}</span>
                  </div>
                )}
              </div>

              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Email
                </label>
                {isEditing ? (
                  <input
                    type="email"
                    name="email"
                    value={editedProfile.email}
                    onChange={handleInputChange}
                    className="w-full p-2 border rounded-md"
                  />
                ) : (
                  <div className="flex items-center">
                    <Mail className="mr-2 text-gray-500" />
                    <span>{userProfile.email}</span>
                  </div>
                )}
              </div>
            </div>

            {/* Render Riwayat Donasi Hanya Jika Bukan Admin */}
            {!isAdmin && (
              <div>
                {donationHistory.map((donation) => (
                  <div
                    key={donation.id}
                    className="border rounded-lg p-4 mb-4 bg-gray-50"
                  >
                    <div className="flex justify-between items-center mb-2">
                      <h3 className="font-bold text-blue-700">
                        {donation.orphanageName}
                      </h3>
                      <span className="text-sm text-gray-600 flex items-center">
                        <Calendar className="mr-1 h-4 w-4" />
                        {donation.date}
                      </span>
                    </div>
                    <div className="flex justify-between items-center">
                      <div className="flex items-center">
                        <DollarSign className="mr-2 text-green-500" />
                        <span>
                          {donation.type === "uang"
                            ? formatCurrency(donation.amount)
                            : `${donation.amount} ${donation.item}`}
                        </span>
                      </div>
                      <span
                        className={`px-3 py-1 rounded-full text-sm ${
                          donation.type === "uang"
                            ? "bg-blue-100 text-blue-800"
                            : "bg-green-100 text-green-800"
                        }`}
                      >
                        {donation.type === "uang"
                          ? "Donasi Dana"
                          : "Donasi Barang"}
                      </span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
