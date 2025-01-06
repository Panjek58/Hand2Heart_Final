import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {
  Mail,
  Lock,
  User,
  Heart,
  Eye,
  EyeOff,
  AlertCircle,
  ArrowLeft,
  HandHeart,
  Building,
} from 'lucide-react';

const Register = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: '',
    email: '',
    phone: '',
    password: '',
    confirmPassword: '',
    role: '',
  });

  const [showPassword, setShowPassword] = useState({
    password: false,
    confirmPassword: false,
  });

  const [errors, setErrors] = useState({});
  const [isLoading, setIsLoading] = useState(false);

  const validateForm = () => {
    const newErrors = {};

    if (!formData.fullName) {
      newErrors.fullName = 'Nama lengkap harus diisi';
    }

    if (!formData.email) {
      newErrors.email = 'Email harus diisi';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Format email tidak valid';
    }

    if (!formData.role) {
      newErrors.role = 'Pilih peran Anda';
    }

    if (!formData.password) {
      newErrors.password = 'Password harus diisi';
    } else if (formData.password.length < 8) {
      newErrors.password = 'Password minimal 8 karakter';
    }

    if (!formData.confirmPassword) {
      newErrors.confirmPassword = 'Konfirmasi password harus diisi';
    } else if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Password tidak cocok';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (validateForm()) {
      setIsLoading(true); // Start loading
      try {
        const response = await axios.post('http://localhost:8080/api/user/register', {
          username: formData.fullName,
          email: formData.email,
          password: formData.password,
          role: formData.role,
          phone: formData.phone, // Include phone number if needed
        });
        console.log('Registration successful:', response.data);
        navigate('/login');
      } catch (error) {
        const errorMessage = error.response?.data?.message || 'Terjadi kesalahan saat registrasi.';
        setErrors({ api: errorMessage });
        console.error('Error during registration:', errorMessage);
      } finally {
        setIsLoading(false); // Stop loading
      }
    }
  };

  const renderInput = (name, label, type = 'text', icon, placeholder) => {
    const isPassword = type === 'password';
    return (
      <div className="mb-6">
        <label className="block text-sm font-medium text-gray-700 mb-2">{label}</label>
        <div className="relative">
          <input
            type={isPassword ? (showPassword[name] ? 'text' : 'password') : type}
            name={name}
            className={`w-full pl-10 ${
              isPassword ? 'pr-12' : 'pr-4'
            } py-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:border-blue-500 outline-none transition ${
              errors[name] ? 'border-red-500' : 'border-gray-300'
            }`}
            placeholder={placeholder}
            value={formData[name]}
            onChange={handleChange}
          />
          {React.cloneElement(icon, {
            className: 'h-5 w-5 text-gray-400 absolute left-3 top-2.5',
          })}
          {isPassword && (
            <button
              type="button"
              className="absolute right-3 top-2.5 text-gray-400 hover:text-gray-600"
              onClick={() =>
                setShowPassword((prev) => ({
                  ...prev,
                  [name]: !prev[name],
                }))
              }
            >
              {showPassword[name] ? <EyeOff className="h-5 w-5" /> : <Eye className="h-5 w-5" />}
            </button>
          )}
        </div>
        {errors[name] && (
          <p className="mt-1 text-sm text-red-500 flex items-center">
            <AlertCircle className="h-4 w-4 mr-1" />
            {errors[name]}
          </p>
        )}
      </div>
    );
  };

  const renderRoleSelection = () => {
    const roles = [
      {
        value: 'donatur',
        label: 'Donatur',
        icon: <HandHeart className="h-8 w-8" />,
        description: 'Saya ingin berdonasi dan membantu',
      },
      {
        value: 'pj_panti',
        label: 'Penanggung Jawab Panti',
        icon: <Building className="h-8 w-8" />,
        description: 'Saya mengelola sebuah panti',
      },
    ];

    return (
      <div className="mb-6">
        <label className="block text-sm font-medium text-gray-700 mb-2">Pilih Peran Anda</label>
        <div className="grid grid-cols-2 gap-4">
          {roles.map((role) => (
            <button
              key={role.value}
              type="button"
              onClick={() =>
                setFormData((prev) => ({
                  ...prev,
                  role: role.value,
                }))
              }
              className={`flex flex-col items-center justify-center p-4 border-2 rounded-lg transition ${
                formData.role === role.value
                  ? 'border-blue-500 bg-blue-50 text-blue-700'
                  : 'border-gray-300 hover:border-blue-300'
              }`}
            >
              {React.cloneElement(role.icon, {
                className: `${
                  formData.role === role.value ? 'text-blue-500' : 'text-gray-400'
                } mb-2`,
              })}
              <span className="font-medium">{role.label}</span>
              <span className="text-xs text-gray-500 mt-1">{role.description}</span>
            </button>
          ))}
        </div>
        {errors.role && (
          <p className="mt-1 text-sm text-red-500 flex items-center">
            <AlertCircle className="h-4 w-4 mr-1" />
            {errors.role}
          </p>
        )}
      </div>
    );
  };

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-6">
      <div className="max-w-md w-full">
        <div className="text-center mb-8">
          <div className="flex justify-center mb-4">
            <div className="bg-blue-500 p-3 rounded-full">
              <Heart className="h-8 w-8 text-white" />
            </div>
          </div>
          <h1 className="text-3xl font-bold text-blue-600">Daftar Akun Baru</h1>
          <p className="text-gray-600 mt-2">Bergabunglah untuk mulai berdonasi</p>
        </div>

        <div className="bg-white shadow-lg rounded-lg p-8">
          <form onSubmit={handleSubmit}>
            {renderInput('fullName', 'Nama Lengkap', 'text', <User />, 'Masukkan nama lengkap')}
            {renderInput('email', 'Email', 'email', <Mail />, 'nama@email.com')}
            {renderRoleSelection()}
            {renderInput('password', 'Password', 'password', <Lock />, 'Minimal 8 karakter')}
            {renderInput(
              'confirmPassword',
              'Konfirmasi Password',
              'password',
              <Lock />,
              'Masukkan ulang password'
            )}
            <button
              type="submit"
              className={`w-full py-2 px-4 rounded-lg transition flex items-center justify-center mt-6 ${
                isLoading ? 'bg-blue-300' : 'bg-blue-500 hover:bg-blue-600'
              } text-white`}
              disabled={isLoading}
            >
              {isLoading ? 'Memproses...' : 'Daftar Sekarang'}
            </button>
            <div className="mt-6 text-center text-sm">
              <span className="text-gray-600">Sudah punya akun? </span>
              <button
                type="button"
                onClick={() => navigate('/login')}
                className="text-blue-600 hover:text-blue-800 font-medium transition"
              >
                Masuk
              </button>
            </div>
          </form>
        </div>

        <div className="mt-6">
          <button
            type="button"
            onClick={() => navigate('/')}
            className="text-gray-600 hover:text-gray-800 flex items-center justify-center w-full"
          >
            <ArrowLeft className="h-4 w-4 mr-2" />
            Kembali ke Beranda
          </button>
        </div>
      </div>
    </div>
  );
};

export default Register;
