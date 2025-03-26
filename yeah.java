public static void obtenerDetallesLocalizacion(Scanner scanner) {
        System.out.print("Ingrese el número de personaje: ");
        int idPersonaje = intValidator.leerInt(scanner);
       
                try {
                    String apiUrl = "https://rickandmortyapi.com/api/character/" + idPersonaje;
                    URL url = new URL(apiUrl);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestMethod("GET");
                    uc.connect();

                    int responseCode = uc.getResponseCode();
                    if (responseCode != 200) {
                        throw new RuntimeException("HTTP Response Code: " + responseCode);
                    }

                    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine;
                    StringBuffer content = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();
                    uc.disconnect();
                    
                    Gson gson = new Gson();
                    JsonObject jsonObject = gson.fromJson(content.toString(), JsonObject.class);
                    
                    JsonObject locationObject = jsonObject.getAsJsonObject("location");
                        String localizacionUrl = locationObject.get("url").getAsString();
                        
                            URL locUrl = new URL(localizacionUrl);
                            HttpURLConnection locUc = (HttpURLConnection) locUrl.openConnection();
                            locUc.setRequestMethod("GET");
                            locUc.connect();
                            
                            

                            BufferedReader locIn = new BufferedReader(new InputStreamReader(locUc.getInputStream()));
                            StringBuffer locContent = new StringBuffer();
                            while ((inputLine = locIn.readLine()) != null) {
                                locContent.append(inputLine);
                            }
                            locIn.close();
                            locUc.disconnect();

                            JsonObject locJsonObject = gson.fromJson(locContent.toString(), JsonObject.class);
                            String nombre = locJsonObject.get("name").getAsString();
                            String tipo = locJsonObject.get("type").getAsString();
                            String dimension = locJsonObject.get("dimension").getAsString();

                            // Mostrar el nombre, tipo y dimensión de la localización
                            System.out.println("\nDetalles de la Localización:");
                            System.out.println("~ Nombre: " + nombre);
                            System.out.println("~ Tipo: " + tipo);
                            System.out.println("~ Dimensión: " + dimension);
                        
                    
                } catch (Exception e) {
                    System.err.println("\nError obteniendo la localización: " + e.getMessage());
                }
    }
